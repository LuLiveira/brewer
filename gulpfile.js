var gulp     = require('gulp');
var uglify   = require('gulp-uglify');
var concat	 = require('gulp-concat');
var sass     = require('gulp-sass');

var jsPath = "./src/main/resources/static/javascript/";
var jsExt = ".js";

var js_dev_1       = jsPath + "brewer" + jsExt;
var js_dev_2       = jsPath + "modal-cadastro-rapido-estilo" + jsExt;
var js_dev_3	   = jsPath + "cerveja-upload-foto" + jsExt;

var js_dist 	  = jsPath;
var js_dist_name1 = "brewer.min" + jsExt;
var js_dist_name2 = "estilo.min" + jsExt;
var js_dist_name3 = "upload-foto.min" + jsExt;

var css_dev  = "./src/main/resources/static/stylesheets/brewer.sass";
var css_dist = "./src/main/resources/static/stylesheets/";
var css_dist_name = "brewer.min.css";

function minifyJS() {
	return gulp.src([js_dev_1])
	.pipe(uglify().on('error', function(error) {
		console.log(error);
	}))
	.pipe(concat(js_dist_name1))
	.pipe(gulp.dest(js_dist));
}

function minifyJS2() {
	return gulp.src([js_dev_2])
	.pipe(uglify().on('error', function(error) {
		console.log(error);
	}))
	.pipe(concat(js_dist_name2))
	.pipe(gulp.dest(js_dist));
}

function minifyJS3() {
	return gulp.src([js_dev_3])
	.pipe(uglify().on('error', function(error) {
		console.log(error);
	}))
	.pipe(concat(js_dist_name3))
	.pipe(gulp.dest(js_dist));
}

function minifyCSS() {
	return gulp.src([css_dev])
			.pipe(sass().on('error', function(error) {
				console.log(error);
			}))
			.pipe(concat(css_dist_name))
			.pipe(gulp.dest(css_dist));
}

gulp.task('minificar-js', function(){
	return minifyJS();
});

gulp.task('minificar-js-2', function(){
	return minifyJS2();
});

gulp.task('minificar-js-3', function(){
	return minifyJS3();
});

gulp.task('minificar-css', function(){
	return minifyCSS();
});

gulp.task('monitor', function(){
	gulp.watch([js_dev_1, js_dev_2, js_dev_3, css_dev], gulp.series('minificar-js', 'minificar-css', 'minificar-js-2', 'minificar-js-3'));
});

gulp.task('default', gulp.series('monitor'));