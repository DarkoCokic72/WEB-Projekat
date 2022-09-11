Vue.component('displayFacility', {
    data: function(){
        return{
            facility: null,
            comments: null,
            workouts: null
        }
    },
    methods: {
        showMap : function() {
            var map = new ol.Map({
            target: 'map',
            layers: [
              new ol.layer.Tile({
                source: new ol.source.OSM()
              })
            ],
            view: new ol.View({
              center: ol.proj.fromLonLat([this.facility.location.longitude, this.facility.location.latitude]),
              zoom: 18
            })
          });
        }
    },
    beforeMount(){
        axios.get("/facilityByName?name=" + this.$route.params.name, {})
        .then(response => {
            if(response.data){
                this.facility = response.data;
            }
        })
    },
    mounted(){
        axios.get("/getAprovedComments?sportFacilityName=" + this.$route.params.name,{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.comments = response.data; 
        })

        axios.get("/getScheduledWorkouts?sportFacilityName=" + this.$route.params.name,{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                this.workouts = response.data; 
        })

        axios.delete("/deleteScheduledWorkouts",{
            contentType:"application/json",
            dataType:"json",
          })
        .then(response => {
                
        })
    },
    template:
    `
    <div>
        <h1>{{facility.name}}</h1>
        <img :src="facility.image"/><br/>
        <h3>Tip: {{facility.type}}</h3>
        <h3>
            Status:
            <span v-if="facility.status">
                <label>Otvoren</label>
            </span>
            <span v-else>
                <label>Zatvoren</label>
            </span>
        </h3>
        <h3>
            Lokacija:
            {{facility.location.city}} {{facility.location.postalCode}},
            {{facility.location.street}} {{facility.location.number}},&nbsp;
            Koordinate:
            {{facility.location.longitude}} {{facility.location.latitude}} &nbsp;
            <button v-on:click="showMap()">Prikaži mapu</button>
        </h3>
        <h3>
            Ocena:
            {{facility.averageScore}}
        </h3>
        <div id="map" class="map"></div>
        <h1>Raspored treninga:</h1>
        <div v-if="workouts.length === 0"><h3>Trenutno ne postoji raspored treninga za sportski objekat!</h3></div>
        <div v-else>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Ime i prezime vežbača</th>
                        <th>Naziv treninga</th>
                        <th>Naziv sportskog objekta</th>
                        <th>Datum i vreme treninga</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="w in workouts">
                        <td>{{w.name}} {{w.surname}}</td>
                        <td>{{w.workout.name}}</td>
                        <td>{{w.workout.sportFacility.name}}</td>
                        <td v-if="w.dateTimeOfWorkout.time.minute === 0">{{w.dateTimeOfWorkout.date.day}}.{{w.dateTimeOfWorkout.date.month}}.{{w.dateTimeOfWorkout.date.year}} {{w.dateTimeOfWorkout.time.hour}}:{{w.dateTimeOfWorkout.time.minute}}0</td>
                        <td v-else>{{w.dateTimeOfWorkout.date.day}}.{{w.dateTimeOfWorkout.date.month}}.{{w.dateTimeOfWorkout.date.year}} {{w.dateTimeOfWorkout.time.hour}}:{{w.dateTimeOfWorkout.time.minute}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <h1>Komentari o sportskom objektu:</h1>
        <div v-if="comments.length === 0"><h3>Još uvek ne postoje komentari za sportski objekat!</h3></div>
        <div v-else>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Komentar</th>
                        <th>Ocena</th>
                        <th>Kupac(ime i prezime - korisničko ime)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="comment in comments">
                        <td>{{comment.text}}</td>
                        <td>{{comment.score}}</td>
                        <td>{{comment.customer.name}} {{comment.customer.surname}} - {{comment.customer.username}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    `
})