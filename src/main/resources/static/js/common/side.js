<s
$(".button-div").click(function(){
  
  $(this).toggleClass("div-close");
  
  if ($(this).hasClass("div-close")) {
    $(".slide-div").animate({ left: "-130px"}, 800);  
    $(".button-div").animate({ left: "-10px"}, 800);
    $(this).find(".button-open").attr("class", "button-close");
  } else {
    $(".slide-div").animate({ left: "0px"}, 800);  
    $(".button-div").animate({ left: "120px"}, 800);
    $(this).find(".button-close").attr("class", "button-open");
  }
});