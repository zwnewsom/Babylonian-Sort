# Test launcher for BabylonianSort, written by Tiger Sachse.
# To use this script, execute the following command:
#     bash Test.sh

# You will need to make sure your directory looks like
# the directory described in the PDF, section 6.

MAX_INPUTS=1
MALFUNCTION=false
INPUT_NUMBERS=$(seq $MAX_INPUTS)

# Omit an error message and change the malfunction flag to true.
function error {
    printf "\nERROR: $1"
    printf "       $2"
    MALFUNCTION=true
}

# If anything has gone wrong, break out of the script.
function malfunction_check {
    if [ "$MALFUNCTION" = true ]
    then
        printf "\nAborting script.\n"
        exit
    fi
}

# Start of the script.
printf "=========================================================\n"
printf "Welcome to the BabylonianSort test script!\n"
printf "=========================================================\n\n"
printf "Checking directory structure.\n"

# Ensure that all inputs are in the right place.
for INPUT in $INPUT_NUMBERS
do
    if [ ! -f "Inputs/Input$INPUT.txt" ]
    then
        error "Inputs/Input$INPUT.java not found.\n" "Refer to PDF section 6.\n"
    fi
done

# Ensure that the submission source code is in the right place.
if [ ! -f "BabylonianSort.java" ]
then
    error "BabylonianSort.java not found.\n" "Refer to PDF section 6.\n"
fi

# Ensure that the test suite is in the right place.
if [ ! -f "BabylonianSortTester.java" ]
then
    error "BabylonianSortTester.java not found.\n" "Refer to PDF section 6.\n"
fi

# If there have been any issues up to this point, this function will exit the script.
malfunction_check

# Clean up the work area.
printf "Cleaning up the work area.\n"
rm -f *.class

# Attempt to compile the program.
printf "Attempting to compile.\n\n"
javac "BabylonianSort.java" "BabylonianSortTester.java"
if [ $? != 0 ]
then
    error "Program failed to compile.\n" "Try compiling on your own.\n" 
fi

# One last check before running the tests.
malfunction_check

# Call the test program.
java BabylonianSortTester

# Post-execution clean up.
rm -f *.class
