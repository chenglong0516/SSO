<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%-- <%@include file="/view/resource.jsp" %> --%>
  </head>
	<body class="easyui-layout">

	<div class="ui-search-panel" region="north">
		<p class="search-title"></p>
		<form id="searchForm" class="form-inline">
				<div class="form-group">
							<label>商品名称:</label> 
							<input class="form-control" type="text" name="name" > 
							<label>商品定价:</label> 
							<input class="form-control" type="text" name="price" > 
							<label>商品描述:</label> 
							<input class="form-control" type="text" name="detail" > 
							<label>商品图片:</label> 
							<input class="form-control" type="text" name="pic" > 
							<label>生产日期:</label> 
							<input class="form-control" type="text" name="createtime" > 
					<a href="#" id="btn-search" class="">查询</a>
				</div>
		</form>
	</div>




	<!--数据表格  -->
     <div region="center" border="false" >
     	<table id="data-list"></table>
     </div>


     <!-- 编辑form -->
     <div id="edit-win" class="hidden easyui-dialog" title="编辑" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:650px;height:450px;">  
     	<form id="editForm" class="ui-form form-horizontal" method="post">   
     		<input type="hidden" class="hidden" name="id">
						<div class="form-group clx">
							<label class="control-label">商品名称:</label>
							<div class="box">
								<input class="form-control" type="text" name="name">
							</div>
						</div>
						<div class="form-group clx">
							<label class="control-label">商品定价:</label>
							<div class="box">
								<input class="form-control" type="text" name="price">
							</div>
						</div>
						<div class="form-group clx">
							<label class="control-label">商品描述:</label>
							<div class="box">
								<input class="form-control" type="text" name="detail">
							</div>
						</div>
						<div class="form-group clx">
							<label class="control-label">商品图片:</label>
							<div class="box">
								<input class="form-control" type="text" name="pic">
							</div>
						</div>
						<div class="form-group clx">
							<label class="control-label">生产日期:</label>
							<div class="box">
								<input class="form-control" type="text" name="createtime">
							</div>
						</div>
     	</form>
  	 </div> 
  </body>
   <script type="text/javascript" src="${staticResourceBaseUrl}/resources/business/items/items.js"></script>
</html>
