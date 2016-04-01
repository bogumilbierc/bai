<#import "masterPage.ftl" as masterPage/>

<@masterPage.masterPage>

<h2>Rejestracja</h2>

    <#if error??>
    ${error}
    </#if>

<form role="form" method="get" action="registerUser">

    <div class="form-group">
        <label for="login">Login:</label>
        <input type="text" class="form-control" name="login">
    </div>
    <div class="form-group">
        <label for="password">Hasło:</label>
        <input type="password" class="form-control" name="password">
    </div>
    <div class="form-group">
        <label for="delay">Opóźnienie po nieudanym logowaniu (w sekundach):</label>
        <input type="number" class="form-control" name="delay" value="3">
    </div>
    <div class="form-group">
        <label for="attempts">Ilość nieudanych logowań, po których ma nastąpić blokada konta:</label>
        <input type="number" class="form-control" name="attempts" value="3">
    </div>

    <button type="submit" class="btn btn-submit">rejestruj</button>
</form>


</@masterPage.masterPage>