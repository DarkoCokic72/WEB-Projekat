Vue.component('schedulingTraining', {
	data: function() {
		return {
			workouts: null,
            dateTimeOfWorkout: '',
            id: ''
		}
	},
	methods: {
		checkForm: function(e) {
			e.preventDefault();
			
			if (!this.id || !this.dateTimeOfWorkout) {
				alert("Morate navesti naziv treninga i datum i vreme treninga!")
				e.preventDefault();
			} else  {
				axios
					.post('/customer/scheduleTraining', {
						id: this.id,
						dateTimeOfWorkout: this.dateTimeOfWorkout
					})
					.then(response => (this.checkRegistrationResponse(response, e)));
			}
        },

        checkRegistrationResponse: function(response, e) {
			if (!response.data) {
				alert("Neuspešno zakazivanje treninga.");
				e.preventDefault();
			}
			else {
				alert("Uspešno zakazan trening.");
				this.$router.push('/mainPage');
			}
		}
		
	},
	mounted (){
        axios.get("/customer/allPersonalWorkouts",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.workouts = response.data; 
        })
		},
	template: 
    `
        <div>
            <h1>Zakazivanje personalnih treninga</h1>
            <form action="#/" method="post" @submit="checkForm">
                <table>
                    <tr>
                        <td>Naziv treninga(sportski objekat - trener):</td>
                        <td>
                            <select v-model="id">
                                <option v-for="item in workouts" :value="item.id">{{item.name}}({{item.sportFacility.name}} - {{item.coach.name}} {{item.coach.surname}})</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Datum i vreme treninga:</td>
                        <td><input type="datetime-local" v-model="dateTimeOfWorkout" name="dateTimeOfWorkout"/></td>
                    </tr>
                    <tr>
				        <td><input type="submit" value="Zakaži"></td>
			        </tr>
                </table>
            </form>
        </div>
	`
})