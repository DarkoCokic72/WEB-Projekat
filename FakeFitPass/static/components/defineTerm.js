Vue.component('defineTerm', {
	data: function() {
		return {
			customerId: '',
            customers: null,
            workouts: null,
            workoutId: ''
		}
	},
	methods: {
		checkForm: function(e) {
			e.preventDefault();
			if (!this.customerId || !this.workoutId) {
				alert("Morate popuniti sva polja!")
				e.preventDefault();
			} else  {
				axios
					.post('/manager/defineTerm', {
						customerId: this.customerId,
						workoutId: this.workoutId
					})
					.then(response => (this.checkRegistrationResponse(response, e)));
			}
        },

        checkRegistrationResponse: function(response, e) {
			if (!response.data) {
				alert("Neuspešno definisan termin, pošto je kupcu istekla članarina ili nema dovoljno termina.");
				e.preventDefault();
                this.$router.push('/mainPage');
			}
			else {
				alert("Uspešno definisan termin.");
				this.$router.push('/mainPage');
			}
		}
		
	},
	mounted (){
        axios.get("/manager/allCustomers",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.customers = response.data;
        })

        axios.get("/manager/allWorkouts",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.workouts = response.data;
        })

        axios.put("/manager/changeStatus",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                
        })

        axios.put("/manager/decreaseNumberOfAppointments",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                
        })
		},
	template: 
    `
        <div>
        <h1>Definisanje termina</h1>
        <form action="#/" method="post" @submit="checkForm">
            <table>
                <tr>
                    <td>Kupac(ime i prezime - korisničko ime):</td>
                    <td>
                        <select v-model="customerId">
                            <option v-for="customer in customers" :value="customer.username">{{customer.name}} {{customer.surname}} - {{customer.username}}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Trening(naziv treninga - tip treninga - trener):</td>
                    <td>
                        <select v-model="workoutId">
                            <option v-for="workout in workouts" :value="workout.id">{{workout.name}} - {{workout.type}} - {{workout.coach.name}} {{workout.coach.surname}}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="Definiši"></td>
                </tr>
            </table>
        </form>
        </div>
	`
})