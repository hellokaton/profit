window.axios.defaults.headers.common = {
    'X-CSRF-TOKEN': document.head.querySelector("[name=csrf_token]").content,
    'X-Requested-With': 'XMLHttpRequest'
};