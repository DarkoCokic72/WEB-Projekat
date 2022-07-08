Vue.component('displayContentForEdit', {
    data: function(){
       return { 
        contents: null,
        jwt: localStorage.getItem('jwt')
       }
    },
    methods: {
        editContent: function(name){
            this.$router.push('/editContent/' + name)
        }
    },
    mounted(){
        axios.get('/obtainContent?jwt=' + this.jwt, {})
            .then(response => {
                this.contents = response.data
            });
    },
    template: `
        <div> 
            <h1>Izaberite trening koji zelite da promenite</h1>
            <table border = "1">
                <thead>
                    <tr>
                        <th>Naziv treninga</th>
                        <th>Tip treninga</th>
                        <th>Trajanje</th>
                        <th>Opis</th>
                        <th>Slika</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for = "c in contents" v-on:click="editContent(c.name)">
                        <td>
                            {{c.name}}
                        </td>
                        <td v-if = "c.type === 'Group'">
                           Grupni
                        </td>
                        <td v-else-if = "c.type === 'Personal'">
                           Personalni
                        </td>
                        <td v-else>
                           Teretana
                        </td>
                        <td>
                            {{c.duration.time.hour}}:{{c.duration.time.minute}}
                        </td>
                        <td>
                            {{c.description}}
                        </td>
                        <td>
                            <img :src="c.image" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    `
})