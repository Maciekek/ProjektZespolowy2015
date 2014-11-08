'use strict';

module.exports = function(grunt) {
	var BASE_DIR = "./";
	var JS_DIR = BASE_DIR + "app/js/";
	var HTML_DIR = BASE_DIR + "app/";
	var SERWER_UNIT_TEST_DIR = BASE_DIR + "test/serwer_unit/";

	grunt.initConfig({
		jshint: {
			all: ['Gruntfile.js', BASE_DIR + 'serwer.js', BASE_DIR + "app/js/*.js"],
			options: {
				jshintrc: '.jshintrc'
			}
		},
		watch: {
			all: {
				files: ['Gruntfile.js', "serwer.js", "app/js/*.js","test/e2e/*.js", "database/dbManager.js"],
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
		jasmine_node: {
			projectRoot: SERWER_UNIT_TEST_DIR,
			requirejs: false,
			forceExit: false,
			jUnit: {
				report: true,
				savePath: SERWER_UNIT_TEST_DIR,
				useDotNotation: true,
				consolidate: true
			}
		}
	});



	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-html-angular-validate');
	grunt.loadNpmTasks("grunt-jasmine-node");
	grunt.loadNpmTasks("grunt-contrib-jshint");
	grunt.registerTask('default', ['jshint','htmlangular']);
	grunt.registerTask('all', ['jshint', 'htmlangular']);
	grunt.registerTask('test', ['jshint', 'jasmine_node']);



};