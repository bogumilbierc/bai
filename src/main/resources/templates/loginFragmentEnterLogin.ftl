<#import "masterPage.ftl" as masterPage/>

<@masterPage.masterPage>

<h2>Logowanie</h2>

<form role="search" action="loginWithFragmentLoginCheck" method="get">
    <div class="form-group">
        <input required type="text" class="form-control" name="login" placeholder="Login">
    </div>
    <button type="submit" class="btn btn-default">Zaloguj</button>
</form>

</@masterPage.masterPage>