<%@page contentType="text/html;charset=UTF-8" %>

<!-- Add "fixed" class to make the sidebar fixed always to the browser viewport. -->
<!-- Adding class "toggle-others" will keep only one menu item open at a time. -->
<!-- Adding class "collapsed" collapse sidebar root elements and show only icons. -->
<div class="sidebar-menu toggle-others collapsed">

    <div class="sidebar-menu-inner">

        <header class="logo-env">

            <%--
            <img src="/assets/images/logo-black@2x.png" width="80" alt="" class="hidden-xs"/>
            <img src="/assets/images/logo-white.png" width="80" alt="" class="visible-xs"/>
            --%>

            <!-- logo -->
            <div class="logo">
                <a href="dashboard-1.html" class="logo-expanded">
                    <img src="/assets/images/logo-white.png" width="80" alt="" />
                </a>

                <a href="dashboard-1.html" class="logo-collapsed">
                    <img src="/assets/images/logo-collapsed@2x.png" width="40" alt="" />
                </a>
            </div>


            <!-- This will toggle the mobile menu and will be visible only on mobile devices -->
            <div class="mobile-menu-toggle visible-xs">
                <a href="#" data-toggle="user-info-menu">
                    <i class="fa-bell-o"></i>
                    <span class="badge badge-success">7</span>
                </a>

                <a href="#" data-toggle="mobile-menu">
                    <i class="fa-bars"></i>
                </a>
            </div>

            <!-- This will open the popup with user profile settings, you can use for any purpose, just be creative -->
            <div class="settings-icon">
                <a href="#" data-toggle="settings-pane" data-animate="true">
                    <i class="linecons-cog"></i>
                </a>
            </div>


        </header>



        <ul id="main-menu" class="main-menu">
            <!-- add class "multiple-expanded" to allow multiple submenus to open -->
            <!-- class "auto-inherit-active-class" will automatically add "active" class for parent elements who are marked already with class "active" -->
            <c:forEach var="permission" items="${LOGINSESSIONINFO.permissions}">
                <li>
                    <c:if test="${permission.status eq 1}">
                        <a href="/${permission.path}.cc">
                            <i class="linecons-cog"></i>
                            <span class="title">${permission.name}</span>
                        </a>
                        <c:if test="${fn:length(permission.childPermissions) > 0}">
                            <ul>
                                <c:forEach items="${permission.childPermissions}" var="childPermission">
                                    <c:if test="${childPermission.isMenu eq 1 and childPermission.status eq 1}">
                                        <li>
                                            <a href="/${childPermission.path}.cc">
                                                <%--<i class="linecons-cog"></i>--%>
                                                <span class="title">${childPermission.name}</span>
                                            </a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </c:if>
                </li>
            </c:forEach>
        </ul>

    </div>

</div>

<!-- Bottom Scripts -->
<script src="/assets/js/TweenMax.min.js"></script>
<script src="/assets/js/resizeable.js"></script>
<script src="/assets/js/xenon-toggles.js"></script>


<!-- JavaScripts initializations and stuff -->
<script src="/assets/js/xenon-custom.js"></script>