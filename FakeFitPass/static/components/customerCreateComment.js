Vue.component("customerCreateComment", {
	data: function () {
		    return {
		      customer: null,
		      sportFacilities: null,
		      isSelected: false,
		      text: '',
		      grade: '',
		      selectedSportFacility: null
		    }
	},
    methods : {
        checkForm: function(e) {
			e.preventDefault();
			
			if (!this.text || !this.grade) {
				alert("Morate navesti komentar i ocenu!")
				e.preventDefault();
			} else  {
				axios
					.post('/customer/createComment', {
						customer: this.customer,
                        selectedSportFacility: this.selectedSportFacility,
                        text: this.text,
                        score: this.grade,
                        isAproved: false,
                        isDenied: false
					})
					.then(response => (this.checkRegistrationResponse(response, e)));
			}
        },

        checkRegistrationResponse: function(response, e) {
			if (!response.data) {
				alert("Neuspešno ostavljen komentar.");
				e.preventDefault();
			}
			else {
				alert("Uspešno ostavljen komentar.");
				this.$router.push('/mainPage');
			}
		}
	},
	mounted () {
         axios
         .get('customer/getCustomer')
         .then(response => { 
			this.customer = response.data; 
		})

        axios.get("/customer/allVisitedSportFacilities",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.sportFacilities = response.data; 
        })
    },
	template: ` 
    <div>
    <h1>Ostavljanje komentara za sportske objekte koje sam posetio/la bar jednom</h1>
    <form action="#/" method="post" @submit="checkForm">
        <table>
            <tr>
                <td>Naziv sportskog objekta:</td>
                <td>
                    <select v-model="selectedSportFacility">
                        <option v-for="sportFacility in sportFacilities" :value="sportFacility">{{sportFacility.name}}</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Komentar:</td>
                <td><textarea name="comment" rows="8" cols="50" placeholder="Leave a comment..." v-model="text"></textarea></td>
            </tr>
            <tr>
                <td>Ocena:</td>
                <td><input type="number" name="quantity" min="1" max="5" v-model="grade"></td>
            </tr>
            <tr>
                <td><input type="submit" value="Ostavi komentar"></td>
            </tr>
        </table>
    </form>
    </div>
`

});