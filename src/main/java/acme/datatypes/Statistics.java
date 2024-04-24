
package acme.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Statistics {

	public Statistics() {
	}


	int		count;

	double	average;

	double	deviation;

	double	minimum;

	double	maximum;

}
