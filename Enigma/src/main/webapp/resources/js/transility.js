/* JavaScript Document */
/* Script written by DSP Rao


/** Calculate client height of wrapper **/
function calHeight() {
	var WindowHeight = $(window).innerHeight();
	var headerH = $(".header").height();
	var heroH = $(".hero").height();
	var hangerH = WindowHeight - (headerH + heroH);	   
	$('.homeWrapper').height(WindowHeight);
	$(".hero").css("top", headerH + "px");
	$('#hanger').css("height", hangerH + "px" );
}    

/** Setting Page top **/	
function setpageTop() {
	var headerH = $(".header").height();
	$(".page").css("top", headerH + "px");
}

/** Setting footer stikcy **/	
function stickFooter() {
	var clientH = $(window).height();
	var headerH = $(".header").height();
	var footerH = $("#footer").height();
	var setpage = clientH - (headerH + footerH)	+ 4;
	$(".pagebody").css("min-height", setpage + "px"); 
}

/** Invoking screen layout calulations on resize **/
$(window).load(function(e) {
	setpageTop();
	calHeight();/* For home page image */
	stickFooter();
});

$(window).resize(function(){
	setpageTop();
	calHeight();/* For home page image */
	stickFooter();
});

/** Scroll shrink**/	
$(function() {
	$(window).scroll(function() {
	var bannerH = $(".banner").css("height", "");
	var scroll = $(window).scrollTop();
	if (scroll > 50) {
	$(".banner").css("height", "0px");
	$(".banner h1").fadeOut(200);
	$(".overflowMenu").removeClass("expandMenu");
	$(".subMenu").removeClass("expandSub");
	
	} else {
	$(".banner").css("height", bannerH + "px");
	$(".banner h1").show();
	}
	});
});

/**Shrink image on scroll**/
$(document).on("scroll", function(){
	if($(document).scrollTop() > 100){
	$(".header").addClass("fillHeader");
	$(".autoScroll").show(200);
	//$(".header").addClass("fadeInDown");
	}
	else
	{
	$(".header").removeClass("fillHeader");
	$(".autoScroll").hide(200);
	//$(".header").removeClass("fadeInDown");
	}
});

/**Invoking Popover**/
function playVDO(Title, URL){
	$(".popOver").show();
	$("#auth").hide();
	$("#media").show();
	$("#media").html('<h3>'+Title+'</h3>'+'<iframe src="'+URL+'" frameborder="0" allowfullscreen></iframe>');
	$('.close').click(function () {
	$(".popOver").hide();
	$("#media").html('');
	});
	}
	
function authVDO(Title, URL){
	$(".popOver").show();
	$("#auth").show();
	$("#media").hide();
	$("#authme").click(function () {
	$("#auth").hide();
	$("#media").show();
	$("#media").html('<h3>'+Title+'</h3>'+'<iframe src="'+URL+'" frameborder="0" allowfullscreen></iframe>');	
	});
	$('.close').click(function () {
	$(".popOver").hide();
	$("#media").html('');
	});
	}	
	
/**Showing PDF in new Window**/
function showDOC(Path){
		window.open(Path,'_blank')
		window.focus();
	}
	
function authDOC(Path){
	$(".popOver").show();
	$("#auth").show();
	$("#media").hide();
	$("#authme").click(function () {
	window.open(Path,'_blank')
	window.focus();		
	$(".popOver").hide();
	});
	$('.close').click(function () {
	$(".popOver").hide();
	});	
	}		

$(document).ready(function(){
/*************************************************************************Document Ready function starts***/

/**Envoking core Screen layout calculation functions**/
	setpageTop();
    calHeight();/* For home page image */
	stickFooter();

/**Back navigation handling***/
$('.back').click(function() {   
	parent.history.back();
	return false;
});

/**Footer HTML***/

$("#footer").html("<div class='content'><ul><li><a href='events.html'>Events</a></li><li><a href='partners.html'>Partners</a></li></ul><div class='group' id='footerBlock'><input type='text' placeholder='Please share your email to help us reach you' id='footermail'><div class='errors' id='footerError'>Error goes here</div><button type='button' id='footersubmit'>Submit</button></div><div id='footerThanks'><p>Thanks you for showing interest.<br>We will get back to you at the earliest.</p></div><div class='group'>Mail us:&nbsp;&nbsp;<a class='cs' href='mailto:info@transility.com'>info@transility.com</a></div><div class='group'><a href='https://www.facebook.com/transility' class='icon linkedin' target='_blank'></a><a href='#twitt' class='icon facebook' target='_blank'></a><a href='https://www.linkedin.com/company/transility' class='icon twitter' target='_blank'></a><a href='https://plus.google.com/109491106999155159624/about' class='icon googleplus' target='_blank'></a></div><div class='copyRight'><small>Â© 2015 Impetus Technologies, Inc. All Rights Reserved</small></div></div>")


/**Scroll to top***/
$('body').on('click', '.autoScroll', function(e) {
	e.preventDefault();
	
	$('html, body').stop().animate({ scrollTop: 0 }, 1000, 'swing');
	// replace 'linear' with 'swing' to see different easing
	});
	$('.autoScroll').hover(function () {
	$('.autoScroll').toggleClass("pulse");
});

/**Scroll to anchor***/
$(function anchorScroll() {
	var hh = $('.mast').height();
	var th = $('.tabs').height();
	var sth = $('.subtabs').height();
	var eh = hh + th + sth;
	$('a[href*=#]:not([href=#])').click(function() {
	if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
	var target = $(this.hash);
	target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
	if (target.length) {
	$('html, body').animate({scrollTop:(target.offset().top - eh)}, 1000);
	return false;
	}
	}
	});
});

/**Scroll to top on nav button clicked**/   
$('body').on('click', '.navTab', function(e) {
	e.preventDefault();
	$('html, body').stop().animate({ scrollTop: 0 }, 1000, 'swing');
	// replace 'linear' with 'swing' to see different easing
});

/**Overflow Menu Interaction**/
$('#overflowMenu').click(function () {
	$(".overflowMenu").toggleClass("expandMenu");
	$("#overflowMenu").toggleClass("rotate90");
	$(".subMenu").removeClass("expandSub");
	});
	$('.subtabs').click(function () {
	$(".subMenu").toggleClass("expandSub");
	$(".down").toggleClass("rotate180");
});

/**Submenu on small screens**/
$('.pagebody').click(function () {
	$(".subMenu").removeClass("expandSub");
	$(".down").removeClass("rotate180");
	$("#overflowMenu").removeClass("rotate90");
	$(".overflowMenu").removeClass("expandMenu");
});

/**Setting Tab heading for small screen**/
$('.navTab').click(function () {
	$('.activeTab').html($(this).html());
	$(".subMenu").toggleClass("expandSub");
	$(".down").toggleClass("rotate180");
});

/**SITE NAVIGATION CONSTRUCTION**/
    $('#em-solutions').show();
    $('#em-services').hide();
    $('#pd-services').show();
    $('#pd-catalysts').hide();
    $('#mt-A').show();
    $('#mt-B').hide();
    $('#mt-C').hide();	
	
/**Enterprise Mobility module**/	
    $('.entTabServices').click(function () {
    $('.entTabServices').addClass("tabActive");
    $('.entTabSolutions').removeClass("tabActive");           
    $('#em-services').show();
	$('html, body').stop().animate({ scrollTop: 0 }, 1000, 'swing');
    $('#em-solutions').hide();
    $('#em-success').hide();
    });

    $('.entTabSolutions').click(function () {
    $('.entTabSolutions').addClass("tabActive");
    $('.entTabServices').removeClass("tabActive");          
    $('#em-solutions').show();
	$('html, body').stop().animate({ scrollTop: 0 }, 1000, 'swing');
    $('#em-services').hide();
    });
    
/**Product Development Module**/    
    $('.devTabServices').click(function () {
    $('.devTabServices').addClass("tabActive");
    $('.devTabCat').removeClass("tabActive");          
    $('#pd-services').show();
	$('html, body').stop().animate({ scrollTop: 0 }, 1000, 'swing');
    $('#pd-catalysts').hide();
    });

    $('.devTabCat').click(function () {
    $('.devTabCat').addClass("tabActive");
    $('.devTabServices').removeClass("tabActive");          
    $('#pd-catalysts').show();
	$('html, body').stop().animate({ scrollTop: 0 }, 1000, 'swing');
    $('#pd-services').hide();
    });

/**Managed Testing Module**/   
    /*$('.tesTabA').click(function () {
    $('.tesTabA').addClass("tabActive");
    $('.tesTabB').removeClass("tabActive");   
    $('.tesTabC').removeClass("tabActive");	       
    $('#mt-A').show();
	$('html, body').stop().animate({ scrollTop: 0 }, 1000, 'swing');
    $('#mt-B').hide();
    $('#mt-C').hide();	
    });
	
    $('.tesTabB').click(function () {
    $('.tesTabB').addClass("tabActive");
    $('.tesTabA').removeClass("tabActive");   
    $('.tesTabC').removeClass("tabActive");	       
    $('#mt-B').show();
	$('html, body').stop().animate({ scrollTop: 0 }, 1000, 'swing');
    $('#mt-A').hide();
    $('#mt-C').hide();	
    });	

    $('.tesTabC').click(function () {
    $('.tesTabC').addClass("tabActive");
    $('.tesTabA').removeClass("tabActive");   
    $('.tesTabB').removeClass("tabActive");	       
    $('#mt-C').show();
	$('html, body').stop().animate({ scrollTop: 0 }, 1000, 'swing');
    $('#mt-A').hide();
    $('#mt-B').hide();	
    });	*/    


	$("#footer").append("<div class='autoScroll animated infinite'><div class='icon up'></div></div>");

/**************************************************************************Document Ready function ends***/
});
