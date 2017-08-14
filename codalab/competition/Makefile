competition/scoring_program.zip: scoring_program/*
	cd ../scorer/ && mvn clean package && cd ../competition
	cp ../scorer/target/scorer-1.0-SNAPSHOT.jar scoring_program/ -v
	cd scoring_program && zip ../competition/scoring_program.zip * && cd ..

competition/dev_data.zip: dev_data/*
	cd dev_data && zip ../competition/dev_data.zip * && cd ..

competition/test_data.zip: test_data/*
	cd test_data && zip ../competition/test_data.zip * && cd ..

competition.zip: competition/* competition/scoring_program.zip competition/dev_data.zip competition/test_data.zip
	cd competition && zip ../competition.zip * && cd ..

submission.zip: submission/*
	cd submission && zip ../submission.zip * && cd ..
