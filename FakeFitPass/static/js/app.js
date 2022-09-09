const Home = {template : '<home></home>'}
const MainPage = {template: '<mainPage></mainPage>'}
const Customer = {template: '<customer/>'}
const Administrator = {template: '<administrator/>'}
const Manager = {template: '<manager/>'}
const Coach = {template: '<coach/>'}
const Registration = {template : '<registration/>'}
const FacilitiesView = {template: '<facilitiesView/>'}
const DisplayFacility = {template: '<displayFacility/>'}
const EditUser = {template: '<editUser/>'}
const AllUsers = {template: '<allUsers/>'}
const NewFacility = {template: '<newFacility/>'}
const NewContent = {template: '<newContent/>'}
const DisplayContentsForEdit = {template: '<displayContentForEdit/>'}
const EditContent = {template: '<editContent/>'}
const CustomerTrainingsView = {template: '<customerTrainingsView/>'}
const CoachTrainingsView = {template: '<coachTrainingsView/>'}
const ManagerTrainingsView = {template: '<managerTrainingsView/>'}
const SchedulingTraining = {template: '<schedulingTraining/>'}
const ScheduledTrainingsViewAndDelete = {template: '<scheduledTrainingsViewAndDelete/>'}
const ManagerSportFacilityView = {template: '<managerSportFacilityView/>'}
const AllWorkouts = {template: '<allWorkouts/>'}
const CreateMembership = {template: '<createMembership/>'}
const DefineTerm = {template: '<defineTerm/>'}
const CustomerCreateComment = {template: '<customerCreateComment/>'}
const AdministratorCommentsView = {template: '<administratorCommentsView/>'}
const CustomerCommentsView = {template: '<customerCommentsView/>'}
const ManagerCommentsView = {template: '<managerCommentsView/>'}
const AdministratorAprovedAndDeniedCommentsView = {template: '<administratorAprovedAndDeniedCommentsView/>'}


const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{path: '/', component: Home},
		{path: '/mainPage', component: MainPage},
		{path: '/customer',name: 'Customer', component: Customer},
		{path: '/admin', name: 'Admin', component: Administrator},
		{path: '/manager',name: 'Manager', component: Manager},
		{path: '/coach',name: 'Coach', component: Coach},
		{path: '/registration', component: Registration},
		{path: '/facilitiesView', component: FacilitiesView},
		{path: '/displayFacility/:name', component: DisplayFacility},
		{path: '/editUser',name: 'EditUser', component: EditUser},
		{path: '/allUsers',name: 'AllUsers', component: AllUsers},
		{path: '/newFacility',name: 'NewFacility', component: NewFacility},
		{path: '/newContent',name: 'NewContent', component: NewContent},
		{path: '/displayContentForEdit',name: 'DisplayContentsForEdit', component: DisplayContentsForEdit},
		{path: '/editContent/:name',name: 'EditContent', component: EditContent},
    {path: '/customerTrainingsView',name: 'CustomerTrainingsView', component: CustomerTrainingsView},
    {path: '/coachTrainingsView',name: 'CoachTrainingsView', component: CoachTrainingsView},
    {path: '/managerTrainingsView',name: 'ManagerTrainingsView', component: ManagerTrainingsView},
    {path: '/schedulingTraining',name: 'SchedulingTraining', component: SchedulingTraining},
    {path: '/scheduledTrainingsViewAndDelete',name: 'ScheduledTrainingsViewAndDelete', component: ScheduledTrainingsViewAndDelete},
    {path: '/managerSportFacilityView',name: 'ManagerSportFacilityView', component: ManagerSportFacilityView},
    {path: '/allWorkouts',name: 'AllWorkouts', component: AllWorkouts},
    {path: '/createMembership',name: 'CreateMembership', component: CreateMembership},
    {path: '/defineTerm',name: 'DefineTerm', component: DefineTerm},
    {path: '/customerCreateComment',name: 'CustomerCreateComment', component: CustomerCreateComment},
    {path: '/administratorCommentsView',name: 'AdministratorCommentsView', component: AdministratorCommentsView},
    {path: '/customerCommentsView',name: 'CustomerCommentsView', component: CustomerCommentsView},
    {path: '/managerCommentsView',name: 'ManagerCommentsView', component: ManagerCommentsView},
    {path: '/administratorAprovedAndDeniedCommentsView',name: 'AdministratorAprovedAndDeniedCommentsView', component: AdministratorAprovedAndDeniedCommentsView}
	  ]
});

const protectedRoutes = ['EditUser', 'AllWorkouts']
const adminRoutes = ['Admin', 'EditUser', 'AllUsers', 'NewFacility', 'AdministratorCommentsView', 'AdministratorAprovedAndDeniedCommentsView']
const managerRoutes = ['Manager', 'NewContent', 'EditContent', 'DisplayContentsForEdit', 'ManagerTrainingsView', 'ManagerSportFacilityView', 'DefineTerm', 'ManagerCommentsView']
const customerRoutes = ['Customer', 'CustomerTrainingsView', 'SchedulingTraining', 'CreateMembership', 'CustomerCreateComment', 'CustomerCommentsView']
const coachRoutes = ['Coach', 'CoachTrainingsView', 'ScheduledTrainingsViewAndDelete']

router.beforeEach((to,from,next) => {
  if(protectedRoutes.includes(to.name)){
    if(window.localStorage.getItem("jwt")!==''){
        next()
    }else{
        alert("Samo Ulogovan korisnik može da pristupi željenoj stranici!")
    }
    }
    else if(adminRoutes.includes(to.name)){
        if(window.localStorage.getItem("role")==='Administrator'){
            next()
        }else{
            alert("Samo Admin može da pristupi željenoj stranici!")
        }
    }
    else if(managerRoutes.includes(to.name)){
        if(window.localStorage.getItem("role")==='Manager'){
            next()
        }else{
            alert("Samo Menadžer može da pristupi željenoj stranici!")
        }
    }else if(customerRoutes.includes(to.name)){
		if(window.localStorage.getItem("role")==='Customer'){
            next()
        }else{
            alert("Samo Kupac može da pristupi željenoj stranici!")
        }
	}else if(coachRoutes.includes(to.name)){
		if(window.localStorage.getItem("role")==='Coach'){
            next()
        }else{
            alert("Samo Trener može da pristupi željenoj stranici!")
        }
	}
    else{
        next()
    }
});

axios.interceptors.request.use(
    (config) => {
      const token =  window.localStorage.getItem('jwt');
      if(token) {
        config.headers.Authorization = `${token}`;
      }
      return config;
    },
    (err) => {
      return Promise.reject(err);
    }
  );

var app = new Vue({
	router,
	el: '#fitpass'
});