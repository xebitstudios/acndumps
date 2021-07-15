appname=$(jq '.app.name' input.json | cut -d '"' -f 2)
appport=$(jq '.app.port' input.json | cut -d '"' -f 2)
routeslist=$(jq '.app.routes[].route' input.json | cut -d '"' -f 2)
svcslist=$(jq '.app.services[].name' input.json | cut -d '"' -f 2)
def0=$(jq '.app.default_route' input.json | cut -d '"' -f 2)
echo - Creating angular app $appname
ng new $appname --routing
echo - Changing into package $appname folder
def1="{ path: '', redirectTo: '/$def0', pathMatch: 'full' }"
echo $def1
cd $appname
echo - Generating components for the number of pages needed in the app
for i in $routeslist
do
	echo ${i}
	ng generate component ${i}
done
svcs="services/"
cd src
cd app
mkdir $svcs
cd $svcs
echo - Generating services needed in the app
for i in $svcslist
do
	echo ${i}
	ng generate service ${i}
done
cd ..
cd ..
cd ..
# Add the new files and routes needed
strt0="Routes = ["
end0="]"
mid0=""
cmm0=", "
echo - Getting path
ls
echo ....................
# Add Bootstrap settings to angular.json file
angjsonfile="angular.json"
angjsonvalue=`cat $angjsonfile`
echo $angjsonvalue
echo ----------------------------
a1='.projects.'
a2='.architect.build.options.styles[0]'
setgs=$(jq $a1$appname$a2 angular.json | cut -d '"' -f 2)
# echo $setgs
echo ----------------------------
newsetgs='src/styles.css",
    "node_modules/bootstrap/dist/css/bootstrap.min.css'
newsetgsfile=${angjsonvalue/$setgs/$newsetgs}
# echo $newsetgsfile
echo ----------------------------
echo $newsetgsfile>angular.json
echo ....................
routfile="app-routing.module.ts"
comp="Component"
cd src
# Add Bootstrap to index.html file
indexfile="index.html"
indexvalue=`cat $indexfile`
orig="<app-root></app-root>"
replce="<app-root></app-root>
	<script src="node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"></script>"
newindexfile=${indexvalue/$orig/$replce}
ls
echo ....................
echo $newindexfile>index.html
# Add global styling to styles.css file
stylesfile="styles.css"
stylesvalue=`cat $stylesfile`
sorig="/* You can add global styles to this file, and also import other style files */"
sreplce="body { padding: 15px; }"
newstylesfile=${stylesvalue/$sorig/$sreplce}
ls
echo ....................
echo $newstylesfile>styles.css
# Add the routes to the routing.ts file
cd app
value=`cat $routfile`
for i in $routeslist
do
	echo $i
	foo=$i
	tring0="{ path: 'dashboard', component: DashboardComponent }"
	tring1=${tring0/dashboard/$i}
	repl0="$(tr '[:lower:]' '[:upper:]' <<< ${foo:0:1})${foo:1}"
	mid1="${tring1/Dashboard/$repl0}"
	mid0=$mid0$cmm0$mid1
	# add the import lines for the components
	oldConst="const"
	newConst="import { $repl0$comp } from './$i/$i.component'; const"
	echo - NewConst....
	echo $newConst
	echo ....................
	value="${value/$oldConst/$newConst}"	
done
echo 
oldString="Routes = []"
newString="Routes = [
	{ path: '', redirectTo: '/dashboard', pathMatch: 'full' },
	{ path: 'dashboard', component: DashboardComponent }
]"
varr=${value/$oldString/$newString}
ls
echo ....................
barr=${value/$oldString/$strt0$def1$mid0$end0}
echo $barr>app-routing.module.ts
# Add httpClient and RxJS to services
cd services
svcts=".service.ts"
# const createURL = 'http://localhost:8100/api/consumer';
# const findAllURL = 'http://localhost:8100/api/consumer/findall';
# Create the service API endpoints to add to service files

echo - Configuring service files
for i in $svcslist
do
	svcfile=`cat $i$svcts`
	oldsvc="import { Injectable } from '@angular/core';"
	newsvc="import { Injectable } from '@angular/core';
		import { HttpClient } from '@angular/common/http';
		import { Observable } from 'rxjs';
		import { analyzeAndValidateNgModules } from '@angular/compiler';"
	varsvc=${svcfile/$oldsvc/$newsvc}
	oldsvc="constructor() { }"
	newsvc="constructor(private httpClient: HttpClient) { }"
	thesvc=${varsvc/$oldsvc/$newsvc}
	echo $thesvc>$i$svcts
done
cd ..
# Add a Navbar to landing page
echo - Navigate to landing page...
cd ..
ls
echo ....................1
cd $def0
ls
echo ....................2
navbr1="<nav class='navbar navbar-expand-lg navbar-light bg-light'>
  <a class='navbar-brand' href='/'>$(tr '[:lower:]' '[:upper:]' <<< ${appname:0:1})${appname:1}</a>
  <button class='navbar-toggler' type='button' data-toggle='collapse' data-target='#navbarNavAltMarkup' aria-controls='navbarNavAltMarkup' aria-expanded='false' aria-label='Toggle navigation'>
    <span class='navbar-toggler-icon'></span>
  </button>
  <div class='collapse navbar-collapse' id='navbarNavAltMarkup'>
    <div class='navbar-nav'>"
navbr2="</div>
  </div>
</nav>
<router-outlet></router-outlet>"
for i in $routeslist
do
	echo - Adding the Navbar Links...
	lnk="<a class='nav-item nav-link' href='/$i'>$(tr '[:lower:]' '[:upper:]' <<< ${i:0:1})${i:1}</a>"
	navbr1=$navbr1$lnk
done
echo - Navbr1...
echo $navbr1
echo .....
nvbr=$navbr1$navbr2
echo $nvbr
echo .....+.....
ls
cd app
echo $nvbr>app.component.html
echo .....+.....
echo "- Done..."
# Add Bootstrap and install all dependencies
echo - Installing bootstrap...
npm install bootstrap

echo - Running npm install...
npm install

echo - Starting $appname app...
ng serve --port $appport
 
