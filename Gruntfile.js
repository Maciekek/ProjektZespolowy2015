'use strict';

module.exports = function(grunt) {
	var BASE_DIR = "./";
	var JS_DIR = BASE_DIR + "app/js/";
	var HTML_DIR = BASE_DIR + "app/";

	grunt.initConfig({
		jshint: {
			all: ['Gruntfile.js', BASE_DIR + 'serwer.js', BASE_DIR + "app/js/*.js"],
			options: {
				jshintrc: '.jshintrc'
			}
		},
		watch: {
			all: {
				files: ['./serwer.js', JS_DIR + '*.js'],
				tasks: ['jshint'],
				options: {
					livereload: true
				},
			},
		},
		htmlangular: {
			options: {
				// Task-specific options go here.
			},
			files: {
				src: [HTML_DIR + '*.html', HTML_DIR + "partials/*.html"],
			},
		},
	});


	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-html-angular-validate');
	grunt.registerTask('default', ['jshint','htmlangular']);
	grunt.registerTask('all', ['jshint', 'htmlangular']);

};