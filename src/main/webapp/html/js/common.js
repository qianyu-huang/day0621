var baseUrl = "http://localhost:8080/"

//规定ajax响应状态码
var ajaxSuccess = 1
var ajaxFail = 0

//layui相关变量
var table = layui.table;
var form = layui.form
var laydate = layui.laydate

// 获取页面的相对路径名字，如student.html
var getPage = function() {
	//获取端口号后面的那部分url
	var pathname = window.location.pathname
	var pathArray = pathname.split("/");

	return pathArray[pathArray.length - 1]
}

//获取按钮权限
var getButtonList = function() {

	//获取按钮权限
	$.get(baseUrl + "/access/getButtonList",{
		page:getPage()
	}, function(res) {

		if(res.code == ajaxSuccess) {

			var buttonArray = JSON.parse(res.msg)

			for(var i = 0; i < buttonArray.length; i++) {
				//解除禁用状态
				$("[name='" + buttonArray[i].attrName + "']").removeClass('layui-btn-disabled')
				$("[name='" + buttonArray[i].attrName + "']").attr('disabled', false)
			}
		} else {
			back()
		}
	})
}