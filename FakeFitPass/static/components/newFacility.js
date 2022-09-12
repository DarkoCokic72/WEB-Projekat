Vue.component('newFacility', {
    data: function(){
        return{
            name: null,
            type: null,
            location: null,
            startTime: null,
            endTime: null,
            image: null,
            manager: null,
            managers: null
        }
    },
    methods: {
        checkResponse: function(response, event) {
			if (!response.data) {
				alert("Neuspešna registracija.");
			}
			else {
				if (this.managers.length === 0) {
					alert("Uspešno registrovan sportski objekat. Međutim, trenutno nema slobodnih menadžera, potrebno kreirati novog koji će biti povezan sa ovim sportskim objektom.")
					this.$router.push('/registration');
					localStorage.setItem("currentFacility", this.name);
				} else {
					alert("Uspešno registrovan sportski objekat.");
                    this.$router.push('/mainPage');
				}
			}
		},

        onChangeFileUpload($event) {
			this.file = this.$refs.file.files[0];
			this.encodeImage(this.file)
		},

        encodeImage(input) {
            if(input) {
                const reader = new FileReader()
                reader.onload = (e) => {
                    this.image = e.target.result
                }
                reader.readAsDataURL(input)
            }
        },

        checkForm: function(e) {
            e.preventDefault();
            if (!this.name || !this.type || !this.location || !this.location.longitude || !this.location.latitude ||
                !this.location.street || !this.location.number || !this.location.city || !this.location.postalCode || !this.startTime || !this.endTime || !this.image) {
                alert("Morate popuniti sva polja")
                e.preventDefault();
            }
            else if (isNaN(parseFloat(this.location.longitude)) || isNaN(parseFloat(this.location.latitude))) {
                alert("Koordinate nisu u dobrom formatu.");
                e.preventDefault();
            }
            else {
                console.log(this.startTime);
                axios
                    .post('/admin/newFacility', {
                        name: this.name,
                        type: this.type,
                        location: this.location,
                        startTime: this.startTime,
                        endTime: this.endTime,
                        image: this.image
                    }, { params: { manager: this.manager } })
                    .then(response => (this.checkResponse(response, e)));
            }
        }
    },
    mounted(){
        this.location = {};
        this.location.longitude = null;
        this.location.latitude = null;
        this.location.street = null;
        this.location.number = null;
        this.location.city = null;
        this.location.postalCode = null;

        axios.get("/admin/freeManagers", {
            headers: {
            },
            contentType: "application/json",
            dataType: "json",
        })
            .then(response => {
                if (response.data) {
                    this.managers = response.data;
                }
            })
    },
    template:
    `
        <div>
            <h1 class="center">Dodaj novi sportski objekat</h1>
            <form action="#/" method="post" @submit="checkForm" class="center">
                <table>
                    <tr>
                        <td>Naziv</td>
                        <td>
                            <input type="text" v-model="name" name="name"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Tip</td>
                        <td>
                            <select v-model="type" name="type">
                                <option value="Gym">Gym</option>
                                <option value="Pool">Pool</option>
                                <option value="SportCentre">SportCentre</option>
                                <option value="DanceStudio">DanceStudio</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Geografska dužina</td>
                        <td><input v-model="location.longitude" type="text"/></td>
                    </tr>
                    <tr>
                        <td>Geografska širina</td>
                        <td><input v-model="location.latitude" type="text"/></td>
                    </tr>
                    <tr>
                        <td>Ulica</td>
                        <td><input v-model="location.street" type="text"/></td>
                    </tr>
                    <tr>
                        <td>Broj zgrade</td>
                        <td><input v-model="location.number" type="text"/></td>
                    </tr>
                    <tr>
                        <td>Grad</td>
                        <td><input v-model="location.city" type="text"/></td>
                    </tr>
                    <tr>
                        <td>Poštanski broj</td>
                        <td><input v-model="location.postalCode" type="text"/></td>
                    </tr>
                    <tr v-if="this.managers.length !== 0">
                        <td>Menadžer</td>
                        <td>
                            <select v-model="manager">
                                <option v-for="item in managers" selected="selected">{{item}}</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Početak radnog vremena</td>
                        <td><input v-model="startTime" name="startTime" type="datetime-local"/></td>
                    </tr>
                    <tr>
                        <td>Kraj radnog vremena</td>
                        <td><input v-model="endTime" name="endTime" type="datetime-local"/></td>
                    </tr>
                    <tr>
                        <td>Logo</td>
                        <input type="file" ref="file" v-on:change="onChangeFileUpload()"/>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Registruj"/></td>
                    </tr>
                </table>
            </form>
        </div>
    `
})