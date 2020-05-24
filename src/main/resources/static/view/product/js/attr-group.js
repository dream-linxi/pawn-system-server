$(function () {
    loadProductPropInfoTable();
});

function loadProductPropInfoTable()
{
    layui.use(['table', 'layer'], function () {
        let table = layui.table         // 表格
            , layer = layui.layer;      // 弹出层

        // 表格初始化
        table.render({
            elem: '#attrGroupTable'   // 表格 ID
            , url: '../../product/productcat/getAllSmallCategoryInfo.json'
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#attrGroupToolBar'    // 工具栏
            , cols: [[  // 表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'catCode', title: '编号', width: "10%", sort: true}
                , {field: 'catName', title: '分类名称', width: "18%", sort: true}
                , {field: 'pCatCode', title: '所属大类', width: "18%", sort: true}
                , {field: 'catLvl', title: '级别', width: "10%", sort: true}
                , {field: 'unit', title: '单位', width: "10%", sort: true}
                , {field: 'sortNo', title: '排序', width: "10%", sort: true}
                , {field: 'isShow', title: '显示', width: "10%", sort: true}
                , {fixed: 'right', width: '10%', align: 'center', title: "操作", toolbar: '#barDemo'}
            ]]
        });


        // 监听商品大类工具栏
        table.on('toolbar(attrGroupTableFilter)', function (obj) {
            let checkStatus = table.checkStatus(obj.config.id);
            let data = checkStatus.data; // 获取当前选中行
            switch (obj.event) {
                case 'showAddAttrGroupInfoWin':
                    showAddAttrGroupInfoWin();
                    break;
                case 'showUpdateAttrGroupInfoWin':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        // layer.alert('编辑 [id]：' + checkStatus.data[0].shopCode);
                        showUpdateAttrGroupInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'deleteAttrGroupInfo':
                    layer.msg('编辑');
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else {
                        let catCodes = [];
                        for (let cat of data) {
                            catCodes.push(cat.catCode);
                        }
                        deleteAttrGroupInfo(catCodes);
                    }
                    break;
            }
            ;
        });


        //监听行工具事件
        table.on('tool(attrGroupTableFilter)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            let data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'detail') {
                location.href="./sub-category.html?catCode=" + data.catCode +"&catName=" + data.catName;
                //console.log(data);
            }
        });

    });
}