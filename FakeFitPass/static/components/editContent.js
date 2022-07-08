Vue.component('editContent', {
    data: function() {
		return {
			name: this.$route.params.name,
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
				alert("Morate navesto naziv, tip i sliku!")
				e.preventDefault();
			} else  {
				axios
					.put('/editContent?jwt=' + this.jwt, {
						name: this.name,
						type: this.type,
						description: this.description,
						duration: this.duration,
                        image: this.image,
                        coach: this.coach
					}, {params: {
								jwt: this.jwt
							}})
					.then(response => (this.checkEditResponse(response, e)));
			}
        },
        checkEditResponse: function(response, event) {
			if (!response.data) {
				alert("Neuspešna izmena sadrzaja.");
				event.preventDefault();
			}
			else {
				alert("Uspešno izmenjen novi sadrzaj.");
				this.$router.push('/mainPage');
			}
		},
        editResponse: function(response){
            this.name = response.data.name;
            if(response.data.type === "Group"){
                this.type = "Group";
            } else if (response.data.type === "Personal"){
                this.type = "Personal";
            } else {
                this.type = "Gym";
            }
            this.image = response.data.image;
            this.description = response.data.description;
            this.duration = response.data.duration;
            this.coach = response.data.coach;
        }
    
    },
    mounted(){
        axios.get("/allCoaches", {
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

        axios.get("/obtainSingleContent?jwt=" + this.jwt + "&name=" + this.name).
            then(response => {this.editResponse(response)})
    },
    template: `
    <div> 
    <h1>Izmena postojeceg sadrzaja</h1>
    <form action = "#/" method = "put" @submit = "checkForm">
        <table>
            <tr>
                <td>Naziv</td>
                <td><input type = "text" v-model = "name" disabled/></td>
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
                    <select v-model="coach" selected="selected">
                        <option v-for="item in coaches">{{item}}</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="Izmeni"></td>
            </tr>
        </table>
    </form>
</div>
    `
})