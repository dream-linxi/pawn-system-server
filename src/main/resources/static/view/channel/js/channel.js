$(function () {
    loadChannelTableInfo();
});


function loadChannelTableInfo()
{
    layui.use(['table', 'layer'], function () {
        let table = layui.table         // 表格
            , layer = layui.layer;      // 弹出层

        // 表格初始化
        table.render({
            elem: '#channelTable'   // 表格 ID
            , url: '../../product/productcat/getAllChannelInfo.json'
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#channelToolBar'    // 工具栏
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
        table.on('toolbar(channelTableFilter)', function (obj) {
            let checkStatus = table.checkStatus(obj.config.id);
            let data = checkStatus.data; // 获取当前选中行
            switch (obj.event) {
                case 'showAddChannelInfoWin':
                    showAddChannelInfoWin();
                    break;
                case 'showUpdateChannelInfoWin':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        // layer.alert('编辑 [id]：' + checkStatus.data[0].shopCode);
                        showUpdateChannelInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'deleteChannelInfo':
                    layer.msg('编辑');
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else {
                        let catCodes = [];
                        for (let cat of data) {
                            catCodes.push(cat.catCode);
                        }
                        deleteChannelInfo(catCodes);
                    }
                    break;
            }
            ;
        });
    });
}

function searchChannelInfoByCondition()
{

}