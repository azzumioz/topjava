// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ],
                "createdRow": function (row, data, dataIndex) {
                    if (($(row).attr("data-userenable")) === 'false') {
                        $(row).css("opacity", "0.5");
                    }
                }
            })
        }
    );

    $('.checked').change(function () {

        let id = $(this).parents().eq(1).attr("id");
        let enabled = $(this).is(":checked");

        $.ajax({
            type: "POST",
            url: context.ajaxUrl + id + "&" + enabled
        })
            .done(function () {
                let patchedRow = $("tr[id=" + id + "]");
                successNoty(enabled ? 'is Enabled' : 'is Disabled');
                enabled ? patchedRow.css("opacity", "1") : patchedRow.css("opacity", "0.5");
            })
            .fail(function () {
                $(this).prop("checked", !enabled);
            });
    })
});