Vue.component('mainPage', {
	data: function() {
		return {
			logged: false,
			role : window.localStorage.getItem("role")
		}
	},
	methods: {
		
		
	},
	
	mounted (){

			sjwt = window.localStorage.getItem('jwt');
			axios.get("/checkJWT",{
			headers: {
				'Authorization': sjwt,
			  },
			  contentType:"application/json",
			dataType:"json",
			  })
			.then(response => {
				if(response.data)
				{ 
					this.logged = true;
					if(window.localStorage.getItem("role")==='Administrator')
					{
					 	this.$router.push('/admin');						
					}
					else if (window.localStorage.getItem("role")==='Manager'){
					 	this.$router.push('/manager');					
					}
					else if (window.localStorage.getItem("role")==='Coach'){
					 	this.$router.push('/coach');					
					}
					else if (window.localStorage.getItem("role")==='Customer'){
					 	this.$router.push('/customer');					
					}
				}
			})
		},
		
	template: `
	     
	`
})