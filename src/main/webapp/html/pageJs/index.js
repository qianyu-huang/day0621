var login = function(){
				
				var username = $("#username").val()
				var password = $("#password").val()
				
				if(username==''){
					
					layer.alert("请输入用户名")
				}else{
					
					if(password==''){
						
						layer.alert("请输入密码")
					}else{
						
						$.post(baseUrl+"/access/login",{
							username:username,
							password:password
						},function(res){
							
							if(res.code == ajaxSuccess){
								
								window.location="main.html"
							}else{
								
								layer.alert(res.msg)
							}
						})
						
					}
					
				}
				
				
			}