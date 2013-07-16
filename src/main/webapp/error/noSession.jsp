<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script>
	window.onload = function() {
		jump(2);
	};
	function jump(count) {
		window.setTimeout(
						function() {
							count--;
							if (count > 0) {
								document.getElementById("num").innerHTML = count;
								jump(count);
							} else {
								window.location.href = "${pageContext.request.contextPath}/login.jsp";
							}
						}, 1000);
	}
</script>
<div id="f">
	<h1>
		登陆超时...<br> 2秒后自动跳转。当前还剩<span id="num">2</span>秒。
	</h1>
</div>