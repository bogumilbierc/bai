<#macro masterPage title="BAI">


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>${title}</title>

    <!-- Bootstrap -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link href="http://getbootstrap.com/examples/sticky-footer-navbar/sticky-footer-navbar.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">bai</a>
        </div>
        <center>
            <div class="navbar-collapse collapse" id="navbar-main">
                <ul class="nav navbar-nav">
                    <#if USER??>
                        <li><a href="userAccountPage">Moje konto</a>
                        </li>
                    </#if>
                    <#--<li class="dropdown">-->
                        <#--<a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>-->
                        <#--<ul class="dropdown-menu">-->
                            <#--<li><a href="#">Action</a>-->
                            <#--</li>-->
                            <#--<li><a href="#">Another action</a>-->
                            <#--</li>-->
                            <#--<li><a href="#">Something else here</a>-->
                            <#--</li>-->
                            <#--<li class="divider"></li>-->
                            <#--<li><a href="#">Separated link</a>-->
                            <#--</li>-->
                            <#--<li class="divider"></li>-->
                            <#--<li><a href="#">One more separated link</a>-->
                            <#--</li>-->
                        <#--</ul>-->
                    <#--</li>-->
                </ul>
                <#if USER??>
                    <form class="navbar-form navbar-right" role="search" action="logout" method="get">
                        <button type="submit" class="btn btn-default">Wyloguj</button>
                    </form>
                <#else>
                    <form class="navbar-form navbar-right" role="search" action="login" method="get">
                        <div class="form-group">
                            <input type="text" class="form-control" name="login" placeholder="Login">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="password" placeholder="Password">
                        </div>
                        <button type="submit" class="btn btn-default">Zaloguj</button>
                    </form>
                </#if>

            </div>
        </center>
    </div>
</nav>

<div class="container">
    <div class="container-fluid">

        <#nested />

    </div>

</div>


<footer class="footer">
    <div class="container">
        <p class="text-muted">&copy; 2016 Bogumił Bierć </p>
    </div>
</footer>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://code.jquery.com/jquery-2.2.2.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
        integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
        crossorigin="anonymous"></script>
</body>

</html>


</#macro>
