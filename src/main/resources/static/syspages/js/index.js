const USER_KEY = 'user_key';
$(function () {
    $('#username').text(JSON.parse(localStorage.getItem(USER_KEY) || '{}'));
    console.log(JSON.parse(localStorage.getItem(USER_KEY) || '{}'))
    loadMenuList();
})

function loadMenuList() {
    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function () {
        var element = layui.element();
        $.get('/sys/menu/getMenuListByCurrentUser.json', function (datas) {
            let result = datas.result;
            let htmlStr = "<ul class='layui-nav layui-nav-tree'>";
            if (result != null || result.length > 0) {
                for (let menu of result) {
                    if (menu.children == null) {
                        htmlStr += "<li class='layui-nav-item layui-this'><a href='javascript:;' data-url='" + menu.url + "'>"
                            + "<i class='iconfont " + menu.defaultImg + "' data-icon='" + menu.defaultImg + "'></i>"
                            + "<span>" + menu.menuName + "</span></a></li>";
                    }
                    if (menu.children != null) {
                        htmlStr += "<li class='layui-nav-item layui-this'><a href='javascript:;' data-url='" + menu.url + "'>"
                            + "<i class='iconfont " + menu.defaultImg + "' data-icon='" + menu.defaultImg + "'></i>"
                            + "<span>" + menu.menuName + "</span></a><em class='layui-nav-more'></em>";
                        htmlStr += "<dl class='layui-nav-child'>";
                        for (let childMenu of menu.children) {
                            htmlStr += "<dd>"
                                + "<a href='javascript:;' data-url='" + childMenu.url + "'>"
                                + "	<i class='iconfont " + childMenu.defaultImg + "' data-icon='" + childMenu.defaultImg + "'></i>"
                                + "	<span>" + childMenu.menuName + "</span></a></dd>";
                        }
                        htmlStr += "</dl>"
                        htmlStr += "</li>"
                    }
                }
            }
            htmlStr += "</ul>"
            $('#larry-nav-side').html(htmlStr);
            element.init('nav','menuTest');
        }, 'json');
       
    });

}


