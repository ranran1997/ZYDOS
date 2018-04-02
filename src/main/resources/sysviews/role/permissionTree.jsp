<c:forEach var=""
<option value="${node1.id }" data-section="${node1.name}" <c:if test="${node1.select == 1}">selected="selected"</c:if> data-index="${node1.lev}">${node1.name }</option>