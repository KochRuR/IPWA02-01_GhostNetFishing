function toggleAnonym() {
	const checkbox = document.getElementById('anonymCheckbox');
	const nameInput = document.getElementById('nameInput');
	const telefonInput = document.getElementById('telefonInput');
	const personenDaten = document.getElementById('personenDaten');

	if (checkbox.checked) {
		personenDaten.style.display = 'none';
		nameInput.required = false;
		nameInput.value = '';
		telefonInput.value = '';
	} else {
		personenDaten.style.display = 'block';
	}
}