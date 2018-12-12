package com.haier.stock.services.Helper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Collections;

import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.haier.stock.util.CertificateUtils;


/**
 * 数字签名助手
 */
public class XmlSignatureHelper {

    private XMLSignatureFactory    fac;
    private DocumentBuilderFactory dbf;

    public XmlSignatureHelper() throws InstantiationException, IllegalAccessException,
                               ClassNotFoundException, NoSuchAlgorithmException,
                               InvalidAlgorithmParameterException {
        // 获取XMLSignature对象
        String providerName = System.getProperty("jsr105Provider",
            "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
        fac = XMLSignatureFactory.getInstance("DOM", (Provider) Class.forName(providerName)
            .newInstance());

        dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
    }

    /**
     * 对报文进行签名
     * @param in 报文的输入流
     * @param keyStorePath 私钥地址
     * @param alias 别名
     * @param password 密码
     * @return 签名后的报文输出流
     * @throws Exception
     */
    public String signature(InputStream in, byte[] keyStoreBytes, String alias, String password)
                                                                                                throws Exception {

        //创建被签名对象
        Document doc = dbf.newDocumentBuilder().parse(in);
        DOMSignContext dsc = new DOMSignContext(CertificateUtils.getPrivateKey(keyStoreBytes,
            alias, password), doc.getFirstChild());

        // 对XML签名
        Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED,
            (TransformParameterSpec) null);
        DigestMethod sha1DigMethod = fac.newDigestMethod(DigestMethod.SHA1, null);
        Reference refToRootDoc = fac.newReference("", sha1DigMethod,
            Collections.singletonList(envelopedTransform), null, null);
        CanonicalizationMethod c14nWithCommentMethod = fac.newCanonicalizationMethod(
            CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS, (C14NMethodParameterSpec) null);
        SignatureMethod dsa_sha1SigMethod = fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null);
        SignedInfo signedInfo = fac.newSignedInfo(c14nWithCommentMethod, dsa_sha1SigMethod,
            Collections.singletonList(refToRootDoc));
        XMLSignature signature = fac.newXMLSignature(signedInfo, null);
        signature.sign(dsc);

        //转化输出
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(out));

        return out.toString("UTF-8");
    }

    /**
    * 验证报文签名
    * @param in 报文的输入流
    * @param certificatePath 证书路径
    * @return 验证是否成功
    * @throws Exception
    */
    public boolean validate(InputStream in, byte[] certificateBytes) throws Exception {
        // 获取XMLSignature对象
        String providerName = System.getProperty("jsr105Provider",
            "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM",
            (Provider) Class.forName(providerName).newInstance());

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);

        Document doc = dbf.newDocumentBuilder().parse(in);

        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

        // 利用公钥进行验证
        Node signatureNode = nl.item(0);
        XMLSignature signature = fac.unmarshalXMLSignature(new DOMStructure(signatureNode));
        DOMValidateContext valContext = new DOMValidateContext(
            CertificateUtils.getPublicKey(certificateBytes), signatureNode);
        return signature.validate(valContext);
    }
}
