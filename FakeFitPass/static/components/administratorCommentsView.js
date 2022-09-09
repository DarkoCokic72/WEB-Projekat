Vue.component('administratorCommentsView', {
	data: function() {
		return {
			comments: null
		}
	},
	methods: {
		"aproveComment": function(id){
            axios.put("/admin/aproveComment?id=" + id,
				    )
					.then(response => {
						if(response.data)
						{ 
                            alert("Uspešno odobren komentar.");
                            window.location.reload();
						}
					})
        },
        "denieComment": function(id){
            axios.put("/admin/denieComment?id=" + id,
				    )
					.then(response => {
						if(response.data)
						{ 
                            alert("Uspešno odbijen komentar.");
                            window.location.reload();
						}
					})
        }
		
	},
	mounted (){
        axios.get("/admin/allComments",{
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
            <h1>Prihvatanje i odbijanje komentara</h1>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Komentar</th>
                        <th>Ocena</th>
                        <th>Kupac(ime i prezime - korisničko ime)</th>
                        <th>Naziv sportskog objekta</th>
                        <th colspan="2">Opcije</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="comment in comments">
                        <td>{{comment.text}}</td>
                        <td>{{comment.score}}</td>
                        <td>{{comment.customer.name}} {{comment.customer.surname}} - {{comment.customer.username}}</td>
                        <td>{{comment.sportFacility.name}}</td>
                        <td><button @click="aproveComment(comment.id)">Odobri</button></td>
                        <td><button @click="denieComment(comment.id)">Odbij</button></td>
                    </tr>
                </tbody>
            </table>
        </div>
	`
})