
var editIndex = undefined;
function endEditing() {
    if (editIndex == undefined) {
        return true
    }
    if ($('#gridView').datagrid('validateRow', editIndex)) {
        var ed = $('#gridView').datagrid('getEditor', {index: editIndex, field: 'productid'});
        // var productname = $(ed.target).combobox('getText');
        // $('#gridView').datagrid('getRows')[editIndex]['productname'] = productname;
        $('#gridView').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
$("#gridView").datagrid({
    onClickRow: function (index, row) {
    	alert("index:"+index)
        if (editIndex != index) {
            if (endEditing()) {
                $('#gridView').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                editIndex = index;
            } else {
                $('#gridView').datagrid('selectRow', editIndex);
            }
        }
    }
});
//function onClickRow(index) {
//    
//}
function append() {


    if (endEditing()) {
        alert(0);
        $('#gridView').datagrid('appendRow', {rowstatus: '1'});
       // alert(1);

        editIndex = $('#gridView').datagrid('getRows').length - 1;
       // alert(2);

        $('#gridView').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
       // alert(1);
    }
}
function append(id,idx,c) {

    if (endEditing()) {
        alert(0);
        $('#gridView').datagrid('appendRow', {rowstatus: '1',id:id,idx:idx});
        alert(1);

        editIndex = $('#gridView').datagrid('getRows').length - 1;
        alert(2);

        $('#gridView').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
        alert(1);
    }
}
function append(id, vtype) {
    //
    if (endEditing()) {
        $('#gridView').datagrid('appendRow', {rowstatus: '1', vtype: vtype, id: id});
        editIndex = $('#gridView').datagrid('getRows').length - 1;
        $('#gridView').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
    }
}
//function SetValue() {
//    if (endEditing()) {
//        alert(1);
//        $('#gridView').datagrid('selectRow', {ecode: '1111'});
//        //editIndex = $('#gridView').datagrid('getRows').length - 1;
//       // $('#gridView').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
//        endEditing();
//        $('#gridView').datagrid('acceptChanges');
//    }
//}
function removeit() {
    if (editIndex == undefined) {
        return
    }
    $('#gridView').datagrid('cancelEdit', editIndex)
        .datagrid('deleteRow', editIndex);
    editIndex = undefined;
}
function accept() {
    if (endEditing()) {
        $('#gridView').datagrid('acceptChanges');
    }
}
function reject() {
    $('#gridView').datagrid('rejectChanges');
    editIndex = undefined;
}
function getChanges() {
    var rows = $('#gridView').datagrid('getChanges');
    // alert(rows.length + ' rows are changed!');
}