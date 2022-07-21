## WLibrary

#### 学习测试打包jitpack
##### Step 1. Add it in your root build.gradle at the end of repositories:
   ```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	```
    或者settings.gradle(gradle>=7.0)配置
    <span style="color:#333333">```
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }```</span>
    

##### Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.moyihen:WLibrary:Tag'
	}