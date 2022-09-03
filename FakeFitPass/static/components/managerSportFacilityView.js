Vue.component('managerSportFacilityView', {
	data: function() {
		return {
			sportFacility: '',
            coaches: null,
            customers: null
		}
	},
	methods: {
		
		
	},
	mounted (){
        axios.get("/manager/getSportFacility",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.sportFacility = response.data; 
        })

        axios.get("/manager/getCoaches",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.coaches = response.data; 
        })

        axios.get("/manager/getCustomers",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.customers = response.data; 
        })
		},
	template: 
    `
        <div>
            <h1>Pregled sportskog objekta</h1>
            <h2>Naziv sportskog objekta: {{sportFacility}}</h2>
            <h2>Treneri koji drže treninge u sportskom objektu:</h2>
            <table>
                <thead>
                    <tr>
                        <th>Ime</th>
                        <th>Prezime</th>
                        <th>Korisničko ime</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="coach in coaches">
                        <td>{{coach.name}}</td>
                        <td>{{coach.surname}}</td>
                        <td>{{coach.username}}</td>
                    </tr>
                </tbody>
            </table>
            <h2>Kupci koji su bar jednom posetili sportski objekat:</h2>
            <table>
                <thead>
                    <tr>
                        <th>Ime</th>
                        <th>Prezime</th>
                        <th>Korisničko ime</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="customer in customers">
                        <td>{{customer.name}}</td>
                        <td>{{customer.surname}}</td>
                        <td>{{customer.username}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
	`
})