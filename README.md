# jenkins-shared-library


shared library helps us to elminate common patterns of code 

# Below is the structure of Shared-Library

        (root)
        +- src                     # Groovy source files
        |   +- org
        |       +- foo
        |           +- Bar.groovy  # for org.foo.Bar class
        +- vars
        |   +- foo.groovy          # for global 'foo' variable
        |   +- foo.txt             # help for 'foo' variable
        +- resources               # resource files (external libraries only)
        |   +- org
        |       +- foo
        |           +- bar.json    # static helper data for org.foo.Bar

'''
The 'src'' directory should look like standard Java source directory structure. This directory is added to the classpath when executing Pipelines.

The 'vars'' directory hosts script files that are exposed as a variable in Pipelines. The name of the file is the name of the variable in the Pipeline. So if you had a file called vars/log.groovy with a function like def info(message)…​ in it, you can access this function like log.info "hello world" in the Pipeline. You can put as many functions as you like inside this file. Read on below for more examples and options.

# O ur Release strategy

1) When you push commits to feature branch : Only lintChecks and codeScan 
2) When changes reach main branch over a PR, that means code it stage and then if we will push a tag marking it stable.
3) When we run the job against a TAG, only during that time, I want artifacts should be created and should be pushed to Nexus.