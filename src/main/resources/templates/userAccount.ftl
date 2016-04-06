<#import "masterPage.ftl" as masterPage/>

<@masterPage.masterPage>

<h2>Twoje konto</h2>

<p>
    login ${user.login}
</p>
    <#if sessionUser.lastLoginDate??>
    <p>
        ostatnie logowanie ${sessionUser.lastLoginDate}
    </p>
    </#if>
    <#if user.lastFailedLoginDate??>
    <p>
        ostatnie nieudane logowanie ${user.lastFailedLoginDate}
    </p>
    </#if>

<h3>edycja</h3>
<form method="get" action="editUser">
    <div class="form-group">
        <label for="delay">Opóźnienie po nieudanym logowaniu (w sekundach):</label>
        <input required type="number" class="form-control" name="delay" value="${user.delayInSeconds}">
    </div>
    <div class="form-group">
        <label for="attempts">Ilość nieudanych logowań, po których ma nastąpić blokada konta:</label>
        <input required type="number" class="form-control" name="attempts" value="${user.numberOfAttempsBeforeBlockade}">
    </div>
    <button type="submit" class="btn btn-submit">zapisz</button>
</form>

<h3>
    zmiana hasła
</h3>
    <#if error??>
    <div color="red">${error}</div>
    </#if>
<form method="get" action="changePassword">
    <div class="form-group">
        <label for="oldPassword">stare hasło:</label>
        <input required type="password" class="form-control" name="oldPassword">
    </div>
    <div class="form-group">
        <label for="password">nowe hasło:</label>
        <input required type="password" class="form-control" name="password">
    </div>
    <button type="submit" class="btn btn-submit">zapisz</button>
</form>



</@masterPage.masterPage>