Vue.component('displayFacility', {
    data: function(){
        return{
            facility: null,
            comments: null
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
            {{facility.location.longitude}} {{facility.location.latitude}}
        </h3>
        <h3>
            Ocena:
            {{facility.averageScore}}
        </h3>
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