$(function () {
    loadChannelTableInfo();
});

/**
 * 初始化,加载渠道商数据信息
 */
function loadChannelTableInfo()
{
    layui.use(['table', 'layer'], function () {
        let table = layui.table         // 表格
            , layer = layui.layer;      // 弹出层

        // 表格初始化
        table.render({
            elem: '#channelTable'   // 表格 ID
            , url: '../../channel/channel/getAllChannelInfo.json'
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#channelToolBar'    // 工具栏
            , cols: [[  // 表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'channelCode', title: '序号', width: "10%", sort: true}
                , {field: 'channelName', title: '渠道名', width: "18%", sort: true}
                , {field: 'mobile', title: '手机', width: "18%", sort: true}
                , {field: 'totalCharge', title: '累计充值', width: "13%", sort: true}
                , {field: 'consume', title: '累计消费', width: "13%", sort: true}
                , {field: 'memberCount', title: '会员数量', width: "13%", sort: true}
                , {field: 'channelStat', title: '状态', width: "11%", sort: true}
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
                        let channelCodes = [];
                        for (let cat of data) {
                            channelCodes.push(cat.channelCode);
                        }
                        deleteChannelInfo(channelCodes);
                    }
                    break;
            }
            ;
        });
    });
}

/**
 * 根据条件查询渠道商信息
 */
function searchChannelInfoByCondition()
{
    layui.use(['table'],function () {
        let table = layui.table;

        table.reload('channelTable',{
            where: {
                "keyWord": $('#keyWord').val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });
}

/**
 * 显示新增渠道商信息
 */
let addChannelInfoLayer;
function showAddChannelInfoWin()
{
    layui.use(['layer'],function () {
        let layer = layui.layer;

        addChannelInfoLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#addChannelInfoWin')  // 内容
            , area: ['auto', '500px']     // 宽高
            , closeBtn: 1   // 关闭按钮
            , shade: 0.3    // 遮罩
            , id: 'LAY_layuipro'    // 唯一 Id 标识
            , resize: true     // 不允许拉伸
            , moveOut: false     // 允许拖至屏外
            , moveType: 1
            , cancel: function () { // 右上关闭操作

            }
        });

    });
}

/**
 * 新增渠道商信息
 */
function addChannelInfo()
{
    layui.use(['table','form','layer'],function () {
        let form = layui.form
            , layer = layui.layer
            , table = layui.table;

        //自定义验证规则
        form.verify({
            checkChannelCode: function (value) {
                let row = 0;
                $.ajax({
                    url: "../../channel/channel/checkChannelCode.json?channelCode=" + value,
                    type: 'get',
                    dataType: 'json',
                    async: false, // 同步
                    success: function (result) {
                        row = result.row;
                    }
                });
                if (row > 0) {
                    return "门店编号已存在";
                }
            }
        });


        form.on('submit(addChannelInfoBtn)', function (data) {

            // 发生 ajax 请求向后台提交数据
            $.post('../../channel/channel/addChannelInfo.json', $('#addChannelInfoForm').serialize(), function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(addChannelInfoLayer);
                    addChannelInfoLayer = "";
                    // 情况表单
                    $("#addChannelInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('channelTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("渠道信息添加成功!!!")
                }
            }, 'json');

            return false;

        });

    });
}

/**
 * 显示更新渠道信息面板
 */
let updateChannelLayer;
function showUpdateChannelInfoWin(data)
{
    layui.use(['form', 'layer'], function () {
        let form = layui.form
            , layer = layui.layer;

        form.val('udpateChannelInfoFilter', data);

        updateChannelLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#updateChannelInfoWin')  // 内容
            , area: ['auto', '500px']     // 宽高
            , closeBtn: 1   // 关闭按钮
            , shade: 0.3    // 遮罩
            , id: 'LAY_layuipro'    // 唯一 Id 标识
            , resize: true     // 不允许拉伸
            , moveOut: false     // 允许拖至屏外
            , moveType: 1
            , cancel: function () { // 右上关闭操作
            }
        });
    });
}

/**
 * 更新渠道信息
 */
function updateChannelInfo()
{
    layui.use(['form', 'table', 'layer'], function () {

        let form = layui.form
            , table = layui.table
            , layer = layui.layer;


        form.on('submit(updateChannelInfoBtn)', function (data) {
            // 发生 ajax 请求向后台提交数据
            $.post('../../channel/channel/updateChannelInfo.json', data.field, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(updateChannelLayer);
                    updateChannelLayer = "";
                    // 情况表单
                    $("#updateChannelInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('channelTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("分类信息更新成功!!!")
                }
            }, 'json');

            return false;
        });
    });
}

/**
 * 删除渠道信息
 */
function deleteChannelInfo(channelCodes)
{
    layui.use(['form', 'table', 'layer'], function () {

        let form = layui.form
            , table = layui.table
            , layer = layui.layer;


        $.post('../../channel/channel/deleteChannelInfoByChannelCodes.json', {"channelCodes": channelCodes} , function (result) {
            if (result.row > 0) {
                // 重新加载 table
                table.reload('channelTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                // 弹出提示信息
                layer.alert("分类删除成功!!!")
            }
        }, 'json');

    });
}