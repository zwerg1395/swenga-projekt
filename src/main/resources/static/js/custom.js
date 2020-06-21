$(function () {

    var forms = $('.needs-validation');
    forms.find('[name]').on('focusout', function () {
        var input = $(this);
        input.removeClass('is-valid is-invalid').addClass(this.checkValidity() ? 'is-valid' : 'is-invalid');
        input.parent().find('.invalid-feedback').remove();
    });

    var initFileDelete = function () {
        $('.deletefile').on('click', function () {
            $(this).closest('tr').remove();
        });
    };
    initFileDelete();

    $('.customfileupload').each(function () {
        $fileupload = $(this);
        $filescontainer = $fileupload.parent().find('.filescontainer');
        $path = $fileupload.data('path');
        $fileupload.fileupload({
            url: '/file',
            paramName: 'file',
            formData: [{
                name: $fileupload.data('csrftokenname'),
                value: $fileupload.data('csrftoken')
            }],
            filesContainer: $filescontainer,
            done: function (e, data) {
                var result = data.response().result;
                var rowIndex = $filescontainer.find('tr').length;
                //window.location = "/refresh";
                $filescontainer.append($('<tr>' +
                    '<td>' +
                    '<input type="hidden" name="' + $path + '[' + rowIndex + '].id" value="' + result.id + '">' +
                    '<input type="hidden" name="' + $path + '[' + rowIndex + '].contentType" value="' + result.contentType + '">' +
                    '<input type="hidden" name="' + $path + '[' + rowIndex + '].size" value="' + result.size + '">' +
                    '<input type="hidden" name="' + $path + '[' + rowIndex + '].originalFileName" value="' + result.originalFileName + '">' +
                    '<a href="/file/' + result.id + '" class="btn btn-link">' + result.originalFileName + '</a>' +
                    '</td>' +
                    '<td>' +
                    '<a href="/file/rename/' + result.id + '?fileName=test123" class="btn btn-danger">Rename</a>' +
                    '<a href="/file/delete/' + result.id + '" class="btn btn-danger">Delete</a>' +
                    '</td>' +
                    '</tr>'));
                initFileDelete();
                document.getElementById("mainForm").submit();
            }
        });
    })

});
