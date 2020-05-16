var gulp     = require('gulp');
var uglify   = require('gulp-uglify');
var concat	 = require('gulp-concat');
var sass     = require('gulp-sass');

var js_dev       = "./src/main/resources/static/javascript/brewer.js";
var js_dist 	 = "./src/main/resources/static/javascript";
var js_dist_name = "brewer.min.js";

var css_dev  = "./src/main/resources/static/stylesheets/brewer.sass";
var css_dist = "./src/main/resources/static/stylesheets";
var css_dist_name = "brewer.min.css";

function minifyJS() {
	return gulp.src([js_dev])
	.pipe(uglify().on('error', function(error) {
		console.log(error);
	}))
	.pipe(concat(js_dist_name))
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

gulp.task('minificar-css', function(){
	return minifyCSS();
});

gulp.task('monitor', function(){
	gulp.watch([js_dev, css_dev], gulp.series('minificar-js', 'minificar-css'));
});

gulp.task('default', gulp.series('monitor'));