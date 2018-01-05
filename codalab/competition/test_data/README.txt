============================================================
How to update reference test data in the running competition
============================================================

As it is not possible to re-build the competition.zip and re-upload it without deleting the
competition on codalab, these steps must be done manually.

* Create a new zip file reference.zip which contains one file truth.txt

$ cd semeval2018-task12/codalab/competition/test_data
$ cp truth_SECRET.txt /tmp/truth.txt
$ zip -j reference.zip /tmp/truth.txt

* Upload the archive to codalab

** Navigate to https://competitions.codalab.org/my/datasets/
** "Create Dataset"
** Add a proper name (description is not required); the type is "Reference data"
** Select the reference.zip file and "Upload"

* Edit and update your competition

** Edit page: https://competitions.codalab.org/competitions/edit_competition/YOUR_COMPETITION_ID
** Navigate to Competition Phase "Test" (or another name you've chosen)
** Select the Reference Data created in the previous step and "Submit"
