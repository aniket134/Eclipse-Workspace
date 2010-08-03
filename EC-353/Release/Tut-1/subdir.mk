################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../Tut-1/Ques-1.cpp \
../Tut-1/Ques-2.cpp 

OBJS += \
./Tut-1/Ques-1.o \
./Tut-1/Ques-2.o 

CPP_DEPS += \
./Tut-1/Ques-1.d \
./Tut-1/Ques-2.d 


# Each subdirectory must supply rules for building sources it contributes
Tut-1/%.o: ../Tut-1/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


