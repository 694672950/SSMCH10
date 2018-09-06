<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fm" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>供应商管理页面 >> 供应商添加页面</span>
        </div>
        <div class="providerAdd">
           <form id="providerForm" name="providerForm" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath }/provider/prodo">
			<input type="hidden" name="method" value="add">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div class="">
                    <label for="proCode">供应商编码：</label>
                    <fm:errors path="proCode"/>
                    <input type="text" name="proCode" id="proCode" value=""> 
					<!-- 放置提示信息 -->
					<font color="red"></font>
                </div>
                <div>
                    <label for="proName">供应商名称：</label>
                    <fm:errors path="proName"/>
                    <input type="text" name="proName" id="proName" value="">
					<font color="red"></font>
                </div>
                <div>
                    <label for="proContact">联系人：</label>
                    <fm:errors path="proContact"/>
                    <input type="text" name="proContact" id="proContact" value=""> 
					<font color="red"></font>

                </div>
                <div>
                    <label for="proPhone">联系电话：</label>
                    <fm:errors path="proPhone"/>
                    <input type="text" name="proPhone" id="proPhone" value=""> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="proAddress">联系地址：</label>
                    <fm:errors path="proAddress"/>
                    <input type="text" name="proAddress" id="proAddress" value=""> 
                </div>
                <div>
                    <label for="proFax">传真：</label>
                    <fm:errors path="proFax"/>
                    <input type="text" name="proFax" id="proFax" value=""> 
                </div>
                <div>
                    <label for="proDesc">描述：</label>
                    <input type="text" name="proDesc" id="proDesc" value=""> 
                </div>
               <div>
                   <label for="proPaths">图片1：</label>
                   <input type="file" name="proPaths" id="proPaths" value="">
               </div>
               <div>
                   <label for="proPath">图片2：</label>
                   <input type="file" name="proPaths" id="proPath" value="">
               </div>
               <div>
                   <span>${msg}</span>
                   <fm:errors/>
               </div>
                <div class="providerAddBtn">
                    <input type="button" name="add" id="add" value="保存">
					<input type="button" id="back" name="back" value="返回" >
                </div>
            </form>
     </div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/provideradd.js"></script>
