Vue.component('newContent', {
    data: function() {
		return {
			name: null,
			type: null,
			image: null,
			description: null,
            duration: null,
            coach: "",
			editMode: false,
			file: null,
			jwt: localStorage.getItem('jwt'),
            coaches: null
			//registerContent: localStorage.getItem('registerContent')
		}
	},
    methods: {
        onChangeFileUpload ($event) {
			this.file = this.$refs.file.files[0];
			this.encodeImage(this.file)
		},
		  encodeImage (input) {
			if (input) {
			  const reader = new FileReader()
			  reader.onload = (e) => {
				this.image = e.target.result
			  }
			  reader.readAsDataURL(input)
			}
		},
          checkForm: function(e) {
			e.preventDefault();
			

			if (!this.name || !this.type || !this.image) {
				alert("Morate navesti naziv, tip i sliku!")
				e.preventDefault();
			} else  {
				axios
					.post('/manager/registerContent', {
						name: this.name,
						type: this.type,
						description: this.description,
						duration: this.duration,
                        image: this.image,
                        coach: this.coach
					})
					.then(response => (this.checkRegistrationResponse(response, e)));
			}
        },
        checkRegistrationResponse: function(response, event) {
			if (!response.data) {
				alert("Neuspešna registracija sadržaja.");
				event.preventDefault();
			}
			else {
				alert("Uspešno registrovan novi sadržaj.");
				this.$router.push('/mainPage');
			}
		}
    },
    mounted(){
        axios.get("manager/allCoaches", {
            headers: {
            },
            contentType: "application/json",
            dataType: "json",
        })
            .then(response => {
                if (response.data) {
                    this.coaches = response.data;
                }
                
            })
    },
    template: `
        <div> 
            <h1>Dodavanje novog sadržaja</h1>
            <form action = "#/" method = "post" @submit = "checkForm">
                <table>
                    <tr>
                        <td>Naziv</td>
                        <td><input type = "text" v-model = "name" /></td>
                    </tr>
                    <tr>
                        <td>Tip treninga</td>
                        <td>
                            <select v-model = "type">
                                <option value = "Group">Grupni trening</option>
                                <option value = "Personal">Personalni trening</option>
                                <option value = "Gym">Teretana</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Slika</td>
                        <td><input type="file" id="file" ref="file" v-on:change="onChangeFileUpload()"/></td>
                    </tr>
                    <tr>
                        <td>Pregled slike</td>
                        <td><img :src="image"></td>
                    </tr>
                    <tr>
                        <td>Opis(opciono)</td>
                        <td><input type = "text" v-model = "description" /></td>
                    </tr>
                    <tr>
                        <td>Trajanje(opciono)</td>
                        <td><input type = "datetime-local" v-model = "duration" /></td>
                    </tr>
                    <tr>
                        <td>Trener(opciono)</td>
                        <td>
                            <select v-model="coach">
                                <option v-for="item in coaches">{{item}}</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
				        <td><input type="submit" value="Dodaj"></td>
			        </tr>
                </table>
            </form>
        </div>
    `
})