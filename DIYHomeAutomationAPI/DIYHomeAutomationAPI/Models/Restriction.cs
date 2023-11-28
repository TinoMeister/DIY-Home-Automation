using DIYHomeAutomationAPI.Models.Enums;

namespace DIYHomeAutomationAPI.Models
{
    /// <summary>
    /// This class represents a Restriction
    /// </summary>
    public class Restriction
    {

        /// <summary>
        /// Restriction's Id
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Restriction's Name
        /// </summary>
        public string Name { get; set; } = null!;

        /// <summary>
        /// Restriction's State
        /// </summary>
        public bool State { get; set; }

        /// <summary>
        /// Restriction's Condition
        /// </summary>
        public TypeCondition Condition { get; set; }

        /// <summary>
        /// Primary Sensor's Id
        /// </summary>
        public int PrimarySensorId { get; set; }

        /// <summary>
        /// Primary Sensor's State
        /// </summary>
        public bool PrimarySensorState { get; set; }

        /// <summary>
        /// Primary Sensor's Value
        /// </summary>
        public double PrimarySensorValue { get; set; }

        /// <summary>
        /// Secondary Sensor's Id
        /// </summary>
        public int SecondarySensorId { get; set; }

        /// <summary>
        /// Secondary Sensor's State
        /// </summary>
        public bool SecondarySensorState { get; set; }

        /// <summary>
        /// Secondary Sensor's Value
        /// </summary>
        public double? SecondarySensorValue { get; set; }
    }
}
