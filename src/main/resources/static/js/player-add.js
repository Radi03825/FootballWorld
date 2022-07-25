let ranges = document.getElementsByClassName('form-range');

 for(let range of ranges) {
    range.addEventListener('change', rangeSlide)
    range.addEventListener('mousemove', rangeSlide)
 }

 function rangeSlide(event) {
    let span = event.target.parentElement.parentElement.getElementsByClassName('rangeValue')[0];
    span.textContent = event.target.value;
 }