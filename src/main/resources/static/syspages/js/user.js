// 页面加载触发
$(function(){

    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //常规用法
        laydate.render({
            elem: '#createStartTime'
        });

        laydate.render({
            elem: '#createEndTime'
        });
    });

    layui.use('table', function(){
        var table = layui.table;
        
        table.render({
          elem: '#test'
          ,url:'/sys/user/getAllUser.json'
          ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
          ,defaultToolbar: ['filter', 'exports', 'print']
          ,title: '用户数据表',
          limits: [8,10,15,20],
          limit: 8,
          id:'userTest'
          ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field:'userId', title:'ID', width:'10%', fixed: 'left', unresize: true, sort: true}
            ,{field:'userName', title:'用户名', width:'15%', edit: 'text'}
            ,{field:'sex', title:'性别', width:'11%', edit: 'text', sort: true}
            ,{field:'phone', title:'手机号码', width:'19%'}
            ,{field:'qqCode', title:'QQ号码', width:'19%'}
            ,{field:'createTime', title:'创建时间', width:'22%', sort: true}
            // ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:'15%'}
          ]]
          ,page: true
        });
        
        //头工具栏事件
        table.on('toolbar(test)', function(obj){
          var checkStatus = table.checkStatus(obj.config.id);
          switch(obj.event){
            case 'showAddUserWin':
              //var data = checkStatus.data;
              //layer.alert(JSON.stringify(data));
              showAddUserWin();
            break;
            case 'showUpdateUserWin':
              //var data = checkStatus.data;
              //layer.msg('选中了：'+ data.length + ' 个');
              showUpdateUserWin();
            break;
            case 'deleteUser':
                deltetUser();
            break;
          };
        });
        
        //监听行工具事件
        // table.on('tool(test)', function(obj){
        //   var data = obj.data;
        //   //console.log(obj)
        //   if(obj.event === 'assigneeRole'){
        //     assigneeRole(data.userId);
        //   }
        // });
      });
});
let updatePhone="";
// 新增用户部分
let addUserLayer;
function showAddUserWin() {
    layui.use(['layer', 'table', 'form'], function () {
        var layer = layui.layer;
        addUserLayer = addLayer = layer.open({
            type: 1
            , title: "新增用户" //不显示标题栏
            , closeBtn: 1 //关闭按钮
            , area: '500' //大小
            , shade: 0.8 //遮住背景
            , id: 'LAY_layuipro' //设定一个id，防止重复弹出
            , btnAlign: 'c' //按钮居中对齐
            , moveType: 1 //拖拽模式，0或者1
            , content: $("#addUserDiv")
        });
    });
}

function addUser() {
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit;
        
        //自定义验证规则
        form.verify({
          checkUserId: function(value){
            let row = 0;
            $.ajax({
                url: "/sys/user/getUserByUserId.json?userId=" + value,
                type: 'get',
                dataType: 'json',
                async: false, // 同步
                success: function (result) {
                    row = result.row;
                }
            });
            if (row > 0) {
                        
                return "用户编号已存在";
            }
          }
          ,password: function(value){
            if(value.length < 3) {
                return '密码不能出现空格，且必须6到10位';
            }
          },
          checkPhone: function(value){
            let row = 0;
            $.ajax({
                url: "/sys/user/getUserByPhone.json?phone=" + value,
                type: 'get',
                dataType: 'json',
                async: false, // 同步
                success: function (result) {
                    row = result.row;
                }
            });
            if (row > 0) {          
                return "该手机号已注册!!!";
            }
          }
        });
        
        //监听提交
        form.on('submit(addUserBtn)', function(data){
            $.post('/sys/user/addUser.json', data.field, function (data) {
                if (data.row === 1) {
                    layui.use(['layer', 'table'], function () {
                        var layer = layui.layer;
                        var table = layui.table;
                        layer.close(addUserLayer);
                        addUserLayer = "";
                        $("#userForm")[0].reset();
                        table.reload('userTest', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        });
                    });
                    layer.alert("用户信息添加成功 ! ! !")
                }
            }, 'json');
            return false;
        });
    });
}

// 修改用户部分

function showUpdateUserWin() {
    layui.use(['layer', 'table', 'form'], function () {
        var layer = layui.layer;
        let table = layui.table;
        let form = layui.form;

        //获取选中的数据行
        var checkStatus = table.checkStatus("userTest");
        var data = checkStatus.data;
        

        if (data.length > 1) {
            layer.msg("只能选择一条数据! ! !");
            return;
        }
        if (data.length < 1) {
            layer.msg("请选择一条数据! ! !");
            return;
        }
        updatePhone = data[0].phone;
        //表单赋值
        form.val("userUpdateFormTest", data[0]);

        layui.use(['layer', 'table', 'form'], function () {
            var layer = layui.layer;
            addLayer = layer.open({
                type: 1
                , title: "修改用户" //不显示标题栏
                , closeBtn: 1 //关闭按钮
                , area: '500' //大小
                , shade: 0.8 //遮住背景
                , id: 'LAY_layuipro1' //设定一个id，防止重复弹出
                , btnAlign: 'c' //按钮居中对齐
                , moveType: 1 //拖拽模式，0或者1
                , content: $("#updateUserDiv")
            });
        });
    });
}

function updateUser(){

    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit;
        
        //自定义验证规则
        form.verify({
          checkUserId: function(value){
            let row = 0;
            $.ajax({
                url: "/sys/user/getUserByUserId.json?userId=" + value,
                type: 'get',
                dataType: 'json',
                async: false, // 同步
                success: function (result) {
                    row = result.row;
                }
            });
            if (row > 0) {
                        
                return "用户编号已存在";
            }
          }
          ,password: function(value){
            if(value.length < 3) {
                return '密码不能出现空格，且必须6到10位';
            }
          },
          checkPhone: function(value){
            let row = 0;
            $.ajax({
                url: "/sys/user/getUserByPhone.json?phone=" + value,
                type: 'get',
                dataType: 'json',
                async: false, // 同步
                success: function (result) {
                    row = result.row;
                }
            });
            if (row > 0) {          
                return "该手机号已注册!!!";
            }
          },checkUpdatePhone: function(value){
            if(updatePhone != value){
                $.ajax({
                    url: "/sys/user/getUserByPhone.json?phone=" + value,
                    type: 'get',
                    dataType: 'json',
                    async: false, // 同步
                    success: function (result) {
                        row = result.row;
                    }
                });
                if (row > 0) {          
                    return "该手机号已注册!!!";
                }
            }
          }
        });
        
        //监听提交
        form.on('submit(updateUserBtn)', function(data){
            $.post('/sys/user/updateUserByUserId.json', $("#updateUserForm").serializeArray(), function (data) {
                if (data.row > 0) {
                    layui.use(['layer', 'table'], function () {
                        var layer = layui.layer;
                        var table = layui.table;
                        layer.close(addLayer);
                        addLayer = "";
                        $("#updateUserForm")[0].reset();
                        table.reload('userTest', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        });
                    });
                }
            }, 'json');
            return false;
        });
    });

    
}

// 删除用户部分
function deltetUser() {
    layui.use(['layer', 'table', 'form'], function () {
        var layer = layui.layer;
        let table = layui.table;
        let form = layui.form;

        var checkStatus = table.checkStatus("userTest");

        var data = checkStatus.data;
        if (data.length < 1) {
            layer.msg("您还未选择删除的数据! ! !");
            return;
        }

        let arr = [];
        for (row of data) {
            arr.push(row.userId);
        }

        $.post('/sys/user/deleteUserByUserIds.json', {"userIds":arr}, function (data) {
            if (data.row > 0) {
                layer.msg("删除成功! ! !");
                table.reload('userTest', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        }, 'json');
    });
}

function searchUserByCondition() {
    layui.use(['layer', 'table', 'form'], function () {
        let table = layui.table;
        table.reload('userTest', {
            where: {
                "keyWord": $('#keyword').val(),
                "createStartTime": $('#createStartTime').val(),
                "createEndTime": $('#createEndTime').val(),
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
}

function assigneeRole(userId)
{
    layer.alert(userId);
    
}