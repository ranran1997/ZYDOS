<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn" %>
<ul class="dropdown-menu notifications">
    <li class="top">
        <p class="small">
            <a href="#" class="pull-right">我知道了</a>
            你有 <strong>${fn:length(undoOrders)}</strong> 条未完成订单.
        </p>
    </li>

    <li>
        <ul class="dropdown-menu-list list-unstyled ps-scrollbar">

            <c:forEach var="undoOrder" items="${undoOrders}">
                <c:choose>
                    <c:when test="${undoOrder.isUrgent eq 1}">
                        <c:set var="classLi" value="notification-primary"/>
                        <c:set var="classI" value="fa-thumbs-up"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="class" value="notification-info"/>
                        <c:set var="classI" value="fa-database"/>
                    </c:otherwise>
                </c:choose>
                <li class="${classLi}">
                    <a href="#">
                        <i class="${classI}"></i>
									<span class="line">
										<strong>${undoOrder.storeName}</strong>
									</span>

									<span class="line small time">
                                            ${undoOrder.addTime}
                                    </span>
                    </a>
                </li>
            </c:forEach>

        </ul>
    </li>
    <li class="external">
        <a href="/orderInfo/list.cc?done=0">
            <span>查看所有未完成订单</span>
            <i class="fa-link-ext"></i>
        </a>
    </li>

</ul>
