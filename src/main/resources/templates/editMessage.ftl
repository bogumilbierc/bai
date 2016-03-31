<#import "masterPage.ftl" as masterPage/>

<@masterPage.masterPage>

<h2>Edycja wiadomości</h2>

<form role="form" method="get" action="editMessage">

    <input type="hidden" name="messageId" value="${message.id}">
    <div class="form-group">
        <textarea class="form-control" rows="5" name="content">${message.content}</textarea>
    </div>
    <button type="submit" class="btn btn-submit">zapisz</button>
</form>



</@masterPage.masterPage>