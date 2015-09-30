// Karma configuration
// Generated on Fri Sep 18 2015 15:17:05 GMT-0600 (MDT)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
  //files: ['bower_components/pact-consumer-js-dsl/dist/pact-consumer-js-dsl.js',
  // { pattern: 'lib*//**/*.js' },
  // { pattern: 'spec*//**/*Spec.js' }],
    files: [
      'bower_components/pact-consumer-js-dsl/dist/pact-consumer-js-dsl.js',
      'bower_components/jquery/dist/jquery.min.js',
      {pattern: 'lib/*.js'},
      {pattern: 'spec/**/*Spec.js'}
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
     browsers: ['Chrome_without_security'],
     customLaunchers: {
        Chrome_without_security: {
            base: 'Chrome',
            flags: ['--disable-web-security']
        }
     },


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  })
}