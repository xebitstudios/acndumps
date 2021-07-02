# jq '.app.boutes[]' input.json | cut -d '"' -f 2
def0=$(jq '.app.default_route' input.json | cut -d '"' -f 2)
def1="{ path: '', redirectTo: '/$def0', pathMatch: 'full' }"
# echo $def1
strt0="Routes = ["
end0="]"
mid0=""
cmm0=","
# str0="$strt0$VAR0$end0"
# echo "$VAR3"
comp="Component"
value=`cat accounts/src/app/app-routing.module.ts`
for i in $(jq '.app.routes[]' input.json | cut -d '"' -f 2)
do
	echo $i #--- "create"
	foo=$i
	# echo ${i} | cut -d '"' -f 2
	tring0="{ path: 'dashboard', component: DashboardComponent }"
	# echo "${tring0/dashboard/$i}"
	tring1=${tring0/dashboard/$i}
	# echo $tring1
	repl0="$(tr '[:lower:]' '[:upper:]' <<< ${foo:0:1})${foo:1}"
	# echo $repl0
	# echo "${tring1/Dashboard/$repl0}"
	mid1="${tring1/Dashboard/$repl0}"
	# echo $strt0$mid0$end0
	mid0=$mid0$cmm0$mid1
	# echo $tring
	ng generate component ${i} | cut -d '"' -f 2
	# add the import lines for the components
	oldConst="const"
	newConst="import { $repl0$comp } from './$i/$i.component'; const"
	# echo $newConst
	value="${value//$oldConst/$newConst}"
	
done

echo 
# value=`cat routing.ts`
# cd "accounts/src/app/"
# value=`cat accounts/src/app/app-routing.module.ts`
# echo "$value"
# echo "---"
oldString="Routes = []"
newString="Routes = [
	{ path: '', redirectTo: '/dashboard', pathMatch: 'full' },
	{ path: 'dashboard', component: DashboardComponent }
]"
echo "${value//$oldString/$newString}" #> ra.ts
rm accounts/src/app/app-routing.module.ts
echo 
"${value//$oldString/$strt0$def1$mid0$end0}" > accounts/src/app/app-routing.module.ts
echo "done..."