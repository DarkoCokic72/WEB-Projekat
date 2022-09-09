Vue.component('administratorAprovedAndDeniedCommentsView', {
	data: function() {
		return {
			comments: null
		}
	},
	mounted (){
        axios.get("/admin/getAprovedAndDeniedComments",{
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
            <h1>Pregled odobrenih i odbijenih komentara</h1>
            <table id="table" border="1">
                <thead>
                    <tr>
                        <th>Komentar</th>
                        <th>Ocena</th>
                        <th>Kupac(ime i prezime - korisničko ime)</th>
                        <th>Naziv sportskog objekta</th>
                        <th>Odobren ili odbijen</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="comment in comments">
                        <td>{{comment.text}}</td>
                        <td>{{comment.score}}</td>
                        <td>{{comment.customer.name}} {{comment.customer.surname}} - {{comment.customer.username}}</td>
                        <td>{{comment.sportFacility.name}}</td>
                        <td v-if="comment.isAproved === true && comment.isDenied === false">Odobren</td>
                        <td v-if="comment.isAproved === false && comment.isDenied === true">Odbijen</td>
                    </tr>
                </tbody>
            </table>
        </div>
	`
})